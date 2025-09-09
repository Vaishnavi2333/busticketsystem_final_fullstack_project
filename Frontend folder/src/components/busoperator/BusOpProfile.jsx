
import { useState, useEffect } from "react";
import BusOperatorService from "../../service/BusOperatorService";


export function BusOpProfile() {
  const [formData, setFormData] = useState({
    name: "",
    companyName: "",
    licenceNumber: "",
    gender: "",
    dateOfBirth: "",
    email: "",
    contactNumber: "",
    address: "",
  });

  const [message, setMessage] = useState("");
  const [licenceError, setLicenceError] = useState("");
  const busOpId = localStorage.getItem("busOpId");

  useEffect(() => {
    const fetchBusOpProfile = async () => {
      if (!busOpId) return;
      try {
        const response = await BusOperatorService.getBusOpById(busOpId);
        if (response.data) {
          setFormData({
            name: response.data.name || "",
            companyName: response.data.companyName || "",
            licenceNumber: response.data.licenceNumber || "",
            gender: response.data.gender || "",
            dateOfBirth: response.data.dateOfBirth || "",
            email: response.data.email || "",
            contactNumber: response.data.contactNumber || "",
            address: response.data.address || "",
          });
        }
      } catch (err) {
        setMessage("Error fetching profile.");
      }
    };
    fetchBusOpProfile();
  }, [busOpId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    if (name === "licenceNumber") {
      setLicenceError("");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");
    setLicenceError("");

    try {
      const payload = { ...formData, busOpLoginId: busOpId };
      await BusOperatorService.updateBusOp(payload);
      setMessage("Profile saved successfully!");
    } catch (err) {
      if (err.response && err.response.data) {
        const errorMsg = err.response.data;
        if (errorMsg.toLowerCase().includes("licence")) {
          setLicenceError(errorMsg);
        } else {
          setMessage(errorMsg);
        }
      } else {
        setMessage("Error saving profile.");
      }
    }
  };

  return (
    <div className="container mt-5" style={{ maxWidth: "700px" }}>
      <h2 className="mb-4 text-center">Bus Operator Profile</h2>
      {message && <div className="alert alert-info text-center">{message}</div>}
      <form onSubmit={handleSubmit}>
        <div className="row mb-3">
          <div className="col-md-6">
            <label className="form-label">Name:</label>
            <input
              type="text"
              name="name"
              className="form-control"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </div>
          <div className="col-md-6">
            <label className="form-label">Company Name:</label>
            <input
              type="text"
              name="companyName"
              className="form-control"
              value={formData.companyName}
              onChange={handleChange}
              required
            />
          </div>
        </div>

        <div className="row mb-3">
          <div className="col-md-6">
            <label className="form-label">Licence Number:</label>
            <input
              type="text"
              name="licenceNumber"
              className={`form-control ${licenceError ? "is-invalid" : ""}`}
              value={formData.licenceNumber}
              onChange={handleChange}
              required
            />
            <small className="form-text text-white">
              Format: <b>XX00 0000000</b> (e.g., MH01 1234567)
            </small>
            {licenceError && (
              <div className="invalid-feedback">{licenceError}</div>
            )}
          </div>
          <div className="col-md-6">
            <label className="form-label">Gender:</label>
            <select
              name="gender"
              className="form-select"
              value={formData.gender}
              onChange={handleChange}
              required
            >
              <option value="">Select</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
              <option value="Other">Other</option>
            </select>
          </div>
        </div>

        <div className="row mb-3">
          <div className="col-md-6">
            <label className="form-label">Date of Birth:</label>
            <input
              type="date"
              name="dateOfBirth"
              className="form-control"
              value={formData.dateOfBirth}
              onChange={handleChange}
              required
            />
          </div>
          <div className="col-md-6">
            <label className="form-label">Email:</label>
            <input
              type="email"
              name="email"
              className="form-control"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>
        </div>

        <div className="row mb-3">
          <div className="col-md-6">
            <label className="form-label">Contact Number:</label>
            <input
              type="text"
              name="contactNumber"
              className="form-control"
              value={formData.contactNumber}
              onChange={handleChange}
              required
            />
          </div>
          <div className="col-md-6">
            <label className="form-label">Address:</label>
            <input
              type="text"
              name="address"
              className="form-control"
              value={formData.address}
              onChange={handleChange}
              required
            />
          </div>
        </div>

        <div className="text-center">
          <button type="submit" className="btn btn-primary btn-lg">
            Save Profile
          </button>
        </div>
      </form>
    </div>
  );
}