import { useEffect, useState } from "react";
import { useNavigate } from "react-router";

import { format } from "date-fns";
import BookingService from "../../service/BookingService";

export default function AdminBookingsPage() {
  const [bookings, setBookings] = useState([]);
  const [displayedBookings, setDisplayedBookings] = useState([]);
  const [searchId, setSearchId] = useState("");
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    fetchAllBookings();
  }, []);

  const fetchAllBookings = async () => {
    try {
      const response = await BookingService.getBookings();
      const data = Array.isArray(response.data) ? response.data : [response.data];
      setBookings(data);
      setDisplayedBookings(data);
      setError("");
    } catch (err) {
      setError("Failed to fetch bookings.");
    }
  };

  const searchBooking = () => {
    if (!searchId) {
      setDisplayedBookings(bookings);
      return;
    }
    const filtered = bookings.filter(
      (b) => b.bookingId.toString() === searchId.toString()
    );
    setDisplayedBookings(filtered);
  };

  const cancelBooking = async (bookingId) => {
    if (!window.confirm("Are you sure you want to cancel this booking?")) return;
    try {
      await BookingService.deleteBooking(bookingId);
      setMessage("Booking cancelled successfully");
      fetchAllBookings();
    } catch (err) {
      setError("Failed to cancel booking.");
    }
  };

  return (
    <div className="container my-5">
      <h2 className="text-center mb-4 fw-bold text-primary">All Bookings (Admin View)</h2>

      {error && <div className="alert alert-danger">{error}</div>}
      {message && <div className="alert alert-success">{message}</div>}

      <div className="d-flex justify-content-center mb-4">
        <input
          type="number"
          placeholder="Enter Booking ID"
          className="form-control w-25 me-2"
          value={searchId}
          onChange={(e) => setSearchId(e.target.value)}
        />
        <button className="btn btn-primary" onClick={searchBooking}>
          Search
        </button>
        <button className="btn btn-secondary ms-2" onClick={() => { setSearchId(""); setDisplayedBookings(bookings); }}>
          Reset
        </button>
      </div>

      <div className="table-responsive shadow-sm rounded">
        <table className="table table-bordered table-hover text-center">
          <thead className="table-dark">
            <tr>
              <th>Booking ID</th>
              <th>User</th>
              <th>Bus</th>
              <th>Booking Date</th>
              <th>Start Time</th>
              <th>End Time</th>
              <th>Seats Booked</th>
              <th>Total Amount</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {displayedBookings.length > 0 ? (
              displayedBookings.map((b) => (
                <tr key={b.bookingId}>
                  <td>{b.bookingId}</td>
                  <td>{b.name || "-"}</td>
                  <td>{b.busName || "-"}</td>
                  <td>{b.bookingDate ? format(new Date(b.bookingDate), "dd/MM/yyyy") : "-"}</td>
                  <td>{b.departureTime || "-"}</td>
                  <td>{b.arrivalTime || "-"}</td>
                  <td>{Array.isArray(b.seatsBooked) ? b.seatsBooked.join(", ") : b.seatsBooked || "-"}</td>
                  <td>{b.totalAmount !== undefined ? b.totalAmount.toFixed(2) : "-"}</td>
                  <td>{b.status || "-"}</td>
                  <td>
                    {b.status !== "CANCELLED" && (
                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() => cancelBooking(b.bookingId)}
                      >
                        Cancel
                      </button>
                    )}
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="10" className="text-center text-muted">
                  No bookings found
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      <button
        className="btn btn-secondary mt-4"
        onClick={() => navigate("/admin/dashboard")}
      >
        Back to Dashboard
      </button>
    </div>
  );
}