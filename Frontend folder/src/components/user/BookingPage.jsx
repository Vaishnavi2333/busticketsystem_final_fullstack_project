
import { useNavigate } from "react-router";
import { useLocation } from "react-router";
import { useEffect } from "react";

import { useState } from "react";
import BookingService from "../../service/BookingService";
import PaymentService from "../../service/PaymentService";
import BusService from "../../service/BusService";

export function BookingPage() {
  const navigate = useNavigate();
  const location = useLocation();

  const {
    bookingId: stateBookingId,
    selectedSeats: stateSeats,
    userId: stateUserId,
    tripId: stateTripId,
    selectedBus: stateBus,
    origin: stateOrigin,
    destination: stateDestination,
    date: stateDate,
  } = location.state || {};

  const storedUserId = localStorage.getItem("userId");
  const userId = stateUserId || storedUserId;
const bookingTripsCache = JSON.parse(localStorage.getItem("bookingTrips") || "{}");
  const cachedTripData = bookingTripsCache[stateTripId] || {};
 const [origin, setOrigin] = useState(stateOrigin || cachedTripData.origin || "");
  const [destination, setDestination] = useState(stateDestination || cachedTripData.destination || "");
  const [date] = useState(stateDate || cachedTripData.date || "");
  const [selectedBus, setSelectedBus] = useState(stateBus || null);
  const [tripId, setTripId] = useState(stateTripId || null);
  const [selectedSeats, setSelectedSeats] = useState(stateSeats || []);
  const [booking, setBooking] = useState(null);
  const [paymentMethod, setPaymentMethod] = useState("Credit Card");
  const [paymentDetails, setPaymentDetails] = useState({});
  const [paymentDone, setPaymentDone] = useState(false);
  const [error, setError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [bookingTrips, setBookingTrips] = useState(() => {
    const saved = localStorage.getItem("bookingTrips");
    return saved ? JSON.parse(saved) : {};
  });

  const updateCache = (tripIdKey, tripData) => {
    setBookingTrips((prev) => {
      const updated = { ...prev, [tripIdKey]: tripData };
      localStorage.setItem("bookingTrips", JSON.stringify(updated));
      return updated;
    });
  };

 useEffect(() => {
    const fetchBooking = async () => {
      try {
        if (stateBookingId) {
          const res = await BookingService.getBookingById(stateBookingId);
          const b = res.data;

          
          setSelectedBus({
            busId: b.busId,
            busName: b.busName || "N/A",
            departureTime: b.departureTime || "N/A",
            arrivalTime: b.arrivalTime || "N/A",
            fare: b.totalPrice / (b.selectedSeats?.length || 1),
            busType: b.busType || "N/A",
            busNumber: b.busNumber || "N/A",
          });
          setTripId(b.tripId);
          setSelectedSeats(b.selectedSeats || []);
          setBooking({
            bookingId: b.bookingId,
            totalPrice: b.totalPrice,
            paymentDone: b.paymentDone,
            selectedSeats: b.selectedSeats || [],
          });
          setPaymentDone(b.paymentDone);

          
          if (bookingTrips[b.tripId]) {
            setOrigin(bookingTrips[b.tripId].origin);
            setDestination(bookingTrips[b.tripId].destination);
          } else if (b.origin && b.destination) {
            setOrigin(b.origin);
            setDestination(b.destination);
            updateCache(b.tripId, { origin: b.origin, destination: b.destination, date: b.bookingDate });
          } else if (b.tripId) {
            const tripRes = await BookingService.getTripById(b.tripId);
            const tripData = {
              origin: tripRes.data.origin,
              destination: tripRes.data.destination,
              date: b.bookingDate,
            };
            setOrigin(tripData.origin);
            setDestination(tripData.destination);
            updateCache(b.tripId, tripData);
          } else {
            setOrigin("Unknown");
            setDestination("Unknown");
          }
        }
      } catch (err) {
        console.error(err);
        setError("Failed to fetch booking details.");
      }
    };
    if (stateBookingId) fetchBooking();
  }, [stateBookingId]);

  if (!selectedBus || !tripId || !selectedSeats || !userId) {
    return (
      <p style={{ textAlign: "center", marginTop: "50px", fontSize: "18px" }}>
        No booking data found. Please select bus and seats first.
      </p>
    );
  }

  const getTodayDate = () => new Date().toISOString().split("T")[0];

  const handleCreateBooking = async () => {
    const bookingDto = {
      userId: parseInt(userId),
      busId: selectedBus.busId,
      tripId: tripId,
      selectedSeats: selectedSeats,
      bookingDate: getTodayDate(),
      status: "Pending",
      totalPrice: selectedSeats.length * selectedBus.fare,
      paymentDone: false,
    };
    try {
      const res = await BookingService.createBooking(bookingDto);
      if (res.data && res.data.bookingId) {
        setBooking({
          bookingId: res.data.bookingId,
          totalPrice: res.data.totalPrice,
          paymentDone: res.data.paymentDone,
          selectedSeats: selectedSeats,
        });
         updateCache(tripId, { origin, destination, date: getTodayDate() });
        setError("");
      } else {
        setError("Booking failed: invalid server response.");
      }
    } catch {
      setError("Booking failed. Try again.");
    }
  };

  const handlePayment = async () => {
    if (!booking || !booking.bookingId) {
      setError("Create booking first.");
      return;
    }
    const paymentDto = {
      bookingId: booking.bookingId,
      amount: booking.totalPrice,
      paymentMethod: paymentMethod,
    };
    try {
      const res = await PaymentService.makePayment(paymentDto);
      setPaymentDone(true);
      setBooking({ ...booking, paymentDone: true });
      setError("");
      setSuccessMessage("✅ Payment successful! Redirecting to confirmation...");
      setTimeout(() => {
        navigate("/confirmation", {
          state: {
            booking: {
              ...booking,
              origin,
              destination,
              date,
              busName: selectedBus.busName,
              selectedSeats: selectedSeats,
            },
            payment: res.data,
          },
        });
      }, 2000);
    } catch {
      setError("Payment failed. Try again.");
    }
  };

  const renderPaymentFields = () => {
    switch (paymentMethod) {
      case "Credit Card":
      case "Debit Card":
        return (
          <div>
            <input
              type="text"
              placeholder="Card Number"
              value={paymentDetails.cardNumber || ""}
              onChange={(e) =>
                setPaymentDetails({ ...paymentDetails, cardNumber: e.target.value })
              }
              className="input"
            />
            <div style={{ display: "flex", gap: "4%" }}>
              <input
                type="text"
                placeholder="Expiry (MM/YY)"
                value={paymentDetails.expiry || ""}
                onChange={(e) =>
                  setPaymentDetails({ ...paymentDetails, expiry: e.target.value })
                }
                className="input"
                style={{ width: "48%" }}
              />
              <input
                type="text"
                placeholder="CVV"
                value={paymentDetails.cvv || ""}
                onChange={(e) =>
                  setPaymentDetails({ ...paymentDetails, cvv: e.target.value })
                }
                className="input"
                style={{ width: "48%" }}
              />
            </div>
          </div>
        );
      case "UPI":
        return (
          <input
            type="text"
            placeholder="Enter UPI ID"
            value={paymentDetails.upiId || ""}
            onChange={(e) =>
              setPaymentDetails({ ...paymentDetails, upiId: e.target.value })
            }
            className="input"
          />
        );
      case "Net Banking":
        return (
          <div style={{ display: "flex", gap: "4%" }}>
            <input
              type="text"
              placeholder="Bank Name"
              value={paymentDetails.bankName || ""}
              onChange={(e) =>
                setPaymentDetails({ ...paymentDetails, bankName: e.target.value })
              }
              className="input"
              style={{ width: "48%" }}
            />
            <input
              type="text"
              placeholder="Account Number"
              value={paymentDetails.accountNumber || ""}
              onChange={(e) =>
                setPaymentDetails({ ...paymentDetails, accountNumber: e.target.value })
              }
              className="input"
              style={{ width: "48%" }}
            />
          </div>
        );
      default:
        return null;
    }
  };

  return (
    <div
      style={{
        maxWidth: "650px",
        margin: "50px auto",
        padding: "35px",
        boxShadow: "0 8px 20px rgba(0,0,0,0.15)",
        borderRadius: "16px",
        backgroundColor: "#fafafa",
        fontFamily: "Arial, sans-serif",
        transition: "0.3s ease",
      }}
    >
      <h2
        style={{
          textAlign: "center",
          color: "#2c3e50",
          marginBottom: "25px",
          fontSize: "26px",
        }}
      >
        Booking & Payment
      </h2>

      <div
        style={{
          marginBottom: "25px",
          padding: "20px",
          border: "1px solid #ddd",
          borderRadius: "10px",
          backgroundColor: "#fff",
        }}
      >
        <h3 style={{ margin: "0 0 10px 0", color: "#34495e" }}>
          {selectedBus.busName}
        </h3>
        <p style={{ margin: "0", color: "#666" }}>Type: {selectedBus.busType}</p>
        <p style={{ margin: "0", color: "#666" }}>Number: {selectedBus.busNumber}</p>
        <p style={{ margin: "0", color: "#666" }}>Start: {selectedBus.departureTime}</p>
        <p style={{ margin: "0", color: "#666" }}>End: {selectedBus.arrivalTime}</p>
        <p style={{ margin: "5px 0 0 0", color: "#666" }}>Trip ID: {tripId}</p>
        <p style={{ margin: "5px 0 0 0", color: "#666" }}>
          Seats: {selectedSeats.join(", ")}
        </p>
        <p style={{ margin: "5px 0 0 0", color: "#666" }}>
          From: <strong>{origin}</strong> → To: <strong>{destination}</strong>
        </p>
      </div>

      {!booking && (
        <button
          onClick={handleCreateBooking}
          style={{
            width: "100%",
            padding: "14px",
            borderRadius: "8px",
            border: "none",
            backgroundColor: "#27ae60",
            color: "#fff",
            fontSize: "18px",
            cursor: "pointer",
            marginTop: "10px",
          }}
        >
          Confirm Booking
        </button>
      )}

      {booking && !paymentDone && !booking.paymentDone && (
        <div style={{ marginTop: "30px" }}>
          <h3 style={{ color: "#34495e", marginBottom: "15px" }}>Payment</h3>
          <p style={{ fontSize: "17px", marginBottom: "15px" }}>
            Total Price: <strong>₹{booking.totalPrice}</strong>
          </p>
          <select
            value={paymentMethod}
            onChange={(e) => setPaymentMethod(e.target.value)}
            style={{
              width: "100%",
              padding: "12px",
              borderRadius: "6px",
              border: "1px solid #ccc",
              marginBottom: "20px",
              fontSize: "16px",
            }}
          >
            <option>Credit Card</option>
            <option>Debit Card</option>
            <option>UPI</option>
            <option>Net Banking</option>
          </select>
          {renderPaymentFields()}
          <button
            onClick={handlePayment}
            disabled={!booking.bookingId || booking.totalPrice <= 0}
            style={{
              width: "100%",
              padding: "14px",
              borderRadius: "8px",
              border: "none",
              backgroundColor: "#2980b9",
              color: "#fff",
              fontSize: "18px",
              cursor: "pointer",
              marginTop: "15px",
            }}
          >
            Pay Now
          </button>
        </div>
      )}

      {successMessage && (
        <p
          style={{
            color: "#27ae60",
            textAlign: "center",
            fontSize: "18px",
            marginTop: "20px",
            fontWeight: "bold",
          }}
        >
          {successMessage}
        </p>
      )}

      {booking?.paymentDone || paymentDone ? (
        <p
          style={{
            color: "#27ae60",
            textAlign: "center",
            fontSize: "18px",
            marginTop: "20px",
          }}
        >
          Booking confirmed!
        </p>
      ) : null}

      {error && (
        <p style={{ color: "red", textAlign: "center", marginTop: "20px" }}>
          {error}
        </p>
      )}
    </div>
  );
}
