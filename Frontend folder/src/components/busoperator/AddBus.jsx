import { useState } from "react";
import { useLocation } from "react-router";
import { useEffect } from "react";
import { useNavigate } from "react-router";
import BusService from "../../service/BusService";

const AddBus = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const editingBus = location.state?.bus;

  const [bus, setBus] = useState({
    busId: null,
    busName: "",
    busNumber: "",
    busType: "",
    capacity: "",
    status: "Available",
  });

  const [amenities, setAmenities] = useState([
    { amenityName: "", busamenityId: null },
  ]);

  const [message, setMessage] = useState("");
  const [messageType, setMessageType] = useState("");

  useEffect(() => {
    if (editingBus) {
      setBus({
        busId: editingBus.busId,
        busName: editingBus.busName,
        busNumber: editingBus.busNumber,
        busType: editingBus.busType,
        capacity: editingBus.capacity,
        status: editingBus.status,
      });

      setAmenities(
        editingBus.amenities && editingBus.amenities.length > 0
          ? editingBus.amenities
          : [{ amenityName: "", busamenityId: null }]
      );
    }
  }, [editingBus]);

  const handleBusChange = (e) => {
    const { name, value } = e.target;
    setBus({ ...bus, [name]: value });
  };

  const handleAmenityChange = (index, value) => {
    const newAmenities = [...amenities];
    newAmenities[index].amenityName = value;
    setAmenities(newAmenities);
  };

  const addAmenityField = () => {
    const last = amenities[amenities.length - 1];
    if (!last || last.amenityName.trim() !== "") {
      setAmenities([...amenities, { amenityName: "", busamenityId: null }]);
    }
  };

  const removeAmenityField = async (index) => {
    const amenity = amenities[index];
    if (amenity.busamenityId) {
      try {
        await BusService.removeBusAmenity(amenity.busamenityId);
      } catch (err) {
        setMessage("Failed to remove amenity");
        setMessageType("error");
        return;
      }
    }
    setAmenities(amenities.filter((_, i) => i !== index));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (bus.busNumber === "0") {
      setMessage("❌ Bus number cannot be 0.");
      setMessageType("error");
      return;
    }

    try {
      let busId;

      if (bus.busId) {
        const updatedBus = await BusService.updateBus(bus);
        busId = updatedBus.data.busId;
      } else {
        const addedBus = await BusService.addBus(bus);
        busId = addedBus.data.busId;
        setBus((prev) => ({ ...prev, busId }));
      }

      const existingAmenities = await BusService.getAmenitiesByBusId(busId);
      const existingNames = existingAmenities.data.map((a) =>
        a.amenityName.trim().toLowerCase()
      );

      for (let a of amenities) {
        if (!a.amenityName) continue;
        const nameLower = a.amenityName.trim().toLowerCase();
        if (!existingNames.includes(nameLower)) {
          await BusService.addBusAmenity({ amenityName: a.amenityName, busId });
        }
      }

      setMessage(bus.busId ? "✅ Bus updated successfully!" : "✅ Bus added successfully!");
      setMessageType("success");

      setTimeout(() => navigate("/busoperator/buses"), 1500);
    } catch (err) {
      console.error("Error saving bus:", err);
      setMessage("❌ Something went wrong while saving the bus.");
      setMessageType("error");
    }
  };

  return (
    <div className="container mt-5" style={{ maxWidth: "700px" }}>
      <h2 className="mb-4 text-center">{bus.busId ? "Update Bus" : "Add Bus"}</h2>

      {message && (
        <div
          className={`alert ${messageType === "success" ? "alert-success" : "alert-danger"}`}
          role="alert"
        >
          {message}
        </div>
      )}

      <form onSubmit={handleSubmit}>
        <div className="row mb-3">
          <div className="col-md-6">
            <input
              type="text"
              name="busName"
              className="form-control"
              placeholder="Bus Name"
              value={bus.busName}
              onChange={handleBusChange}
              required
            />
          </div>
          <div className="col-md-6">
            <input
              type="text"
              name="busNumber"
              className="form-control"
              placeholder="Bus Number"
              value={bus.busNumber}
              onChange={handleBusChange}
              required
            />
          </div>
        </div>

        <div className="row mb-3">
          <div className="col-md-6">
            <input
              type="text"
              name="busType"
              className="form-control"
              placeholder="Bus Type"
              value={bus.busType}
              onChange={handleBusChange}
              required
            />
          </div>
          <div className="col-md-6">
            <input
              type="number"
              name="capacity"
              className="form-control"
              placeholder="Capacity"
              value={bus.capacity}
              onChange={handleBusChange}
              required
            />
          </div>
        </div>

        <div className="mb-3">
          <select
            name="status"
            className="form-select"
            value={bus.status}
            onChange={handleBusChange}
          >
            <option value="Available">Available</option>
            <option value="Unavailable">Unavailable</option>
          </select>
        </div>

        <h4 className="mt-4">Amenities</h4>
        {amenities.map((a, idx) => (
          <div key={idx} className="mb-2 d-flex">
            <input
              type="text"
              className="form-control me-2"
              placeholder={`Amenity ${idx + 1}`}
              value={a.amenityName}
              onChange={(e) => handleAmenityChange(idx, e.target.value)}
            />
            <button
              type="button"
              className="btn btn-danger"
              onClick={() => removeAmenityField(idx)}
            >
              Remove
            </button>
          </div>
        ))}
        <div className="mb-3">
          <button
            type="button"
            className="btn btn-secondary"
            onClick={addAmenityField}
          >
            + Add Amenity
          </button>
        </div>

        <div className="text-center">
          <button type="submit" className="btn btn-primary btn-lg">
            {bus.busId ? "Update Bus" : "Add Bus"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddBus;