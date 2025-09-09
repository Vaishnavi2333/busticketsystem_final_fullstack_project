import { useState,useEffect } from "react";

import BusService from "../../service/BusService";


export default function AdminBusManagement() {
  const [buses, setBuses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [searchId, setSearchId] = useState("");

  
  const fetchBuses = async () => {
    try {
      setLoading(true);
      const response = await BusService.getAllBus();
      setBuses(response.data);
      setLoading(false);
    } catch (err) {
      setError("Failed to fetch buses");
      setLoading(false);
    }
  };

  // Delete bus
  const deleteBus = async (id) => {
    if (window.confirm(`Are you sure you want to delete bus ${id}?`)) {
      try {
        await BusService.deleteBus(id);
        setBuses(buses.filter((bus) => bus.busId !== id));
      } catch (err) {
        alert("Failed to delete bus");
      }
    }
  };

  
  const handleSearch = async () => {
    if (!searchId) {
      fetchBuses();
      return;
    }

    try {
      const response = await BusService.getBusByBusId(searchId);
      if (response.data) {
        setBuses([response.data]); 
        setError("");
      } else {
        setBuses([]);
        setError("No bus found with this ID");
      }
    } catch (err) {
      setError("Failed to fetch bus by ID");
    }
  };

  useEffect(() => {
    fetchBuses();
  }, []);

  if (loading) return <p className="text-center mt-4">Loading buses...</p>;
  if (error) return <p className="text-danger text-center mt-4">{error}</p>;

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4 fw-bold text-primary">Manage Buses</h2>

      
      <div className="d-flex justify-content-center mb-4">
        <input
          type="text"
          className="form-control w-25 me-2"
          placeholder="Enter Bus ID"
          value={searchId}
          onChange={(e) => setSearchId(e.target.value)}
        />
        <button className="btn btn-primary me-2" onClick={handleSearch}>
          Search
        </button>
        <button className="btn btn-secondary" onClick={fetchBuses}>
          Reset
        </button>
      </div>

      {/* Table */}
      <table className="table table-bordered table-striped shadow-sm">
        <thead className="table-primary text-center">
          <tr>
            <th>Bus ID</th>
            <th>Bus Name</th>
            <th>Bus Number</th>
            <th>Bus Type</th>
            <th>Capacity</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody className="text-center align-middle">
          {buses.map((bus) => (
            <tr key={bus.busId}>
              <td>{bus.busId}</td>
              <td>{bus.busName}</td>
              <td>{bus.busNumber}</td>
              <td>{bus.busType}</td>
              <td>{bus.capacity}</td>
              <td>
                <span
                  className={`badge ${
                    bus.status === "ACTIVE" ? "bg-success" : "bg-secondary"
                  }`}
                >
                  {bus.status}
                </span>
              </td>
              <td>
                <button
                  className="btn btn-sm btn-danger"
                  onClick={() => deleteBus(bus.busId)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
          {buses.length === 0 && (
            <tr>
              <td colSpan="7" className="text-center text-muted">
                No buses available
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}