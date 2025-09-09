import { useState,useEffect } from "react";
import TripService from "../../service/TripService";
export default function AdminTrips() {
  const [trips, setTrips] = useState([]);
  const [error, setError] = useState("");
  const [searchId, setSearchId] = useState("");

  const fetchTrips = async () => {
    try {
      const response = await TripService.getAllTrips();
      setTrips(response.data);
      setError("");
    } catch (err) {
      setError("Failed to fetch trips");
    }
  };

  useEffect(() => {
    fetchTrips();
  }, []);

  const deleteTrip = async (id) => {
    if (window.confirm(`Are you sure you want to delete trip ${id}?`)) {
      try {
        await TripService.deleteTrip(id);
        setTrips(trips.filter((trip) => trip.tripId !== id));
        setError("");
      } catch (err) {
        setError("Failed to delete trip");
      }
    }
  };

  const handleSearch = async () => {
    if (!searchId) {
      fetchTrips();
      return;
    }
    try {
      const response = await TripService.getTripByTripId(searchId);
      if (response.data) {
        setTrips([response.data]);
        setError("");
      } else {
        setTrips([]);
        setError(`Trip with ID ${searchId} does not exist`);
      }
    } catch (err) {
      setError("Failed to fetch trip by ID");
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4 fw-bold text-primary">Manage Trips</h2>

      <div className="d-flex justify-content-center mb-4">
        <input
          type="text"
          className="form-control w-25 me-2"
          placeholder="Enter Trip ID"
          value={searchId}
          onChange={(e) => setSearchId(e.target.value)}
        />
        <button className="btn btn-primary me-2" onClick={handleSearch}>
          Search
        </button>
        <button className="btn btn-secondary" onClick={fetchTrips}>
          Reset
        </button>
      </div>

      {error && (
        <div className="alert alert-danger text-center w-50 mx-auto">
          {error}
        </div>
      )}

      <table className="table table-bordered table-striped shadow-sm mt-3">
        <thead className="table-primary text-center">
          <tr>
            <th>ID</th>
            <th>Bus ID</th>
            <th>Date</th>
            <th>Departure</th>
            <th>Arrival</th>
            <th>Fare</th>
            <th>Route ID</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody className="text-center align-middle">
          {trips.map((trip) => (
            <tr key={trip.tripId}>
              <td>{trip.tripId}</td>
              <td>{trip.busId}</td>
              <td>{trip.date}</td>
              <td>{trip.departureTime}</td>
              <td>{trip.arrivalTime}</td>
              <td>₹ {trip.fare}</td>
              <td>{trip.routeId ?? trip.route?.routeId}</td>
              <td>
                <span
                  className={`badge ${
                    trip.status === "ACTIVE" ? "bg-success" : "bg-secondary"
                  }`}
                >
                  {trip.status}
                </span>
              </td>
              <td>
                <button
                  className="btn btn-sm btn-danger"
                  onClick={() => deleteTrip(trip.tripId)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
          {trips.length === 0 && (
            <tr>
              <td colSpan="9" className="text-center text-muted">
                No trips available
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}