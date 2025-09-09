import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import RegistrationPage from "./RegistrationPage";
import LoginPage from "./LoginPage";
import UserDashboard from "../user/UserDashboard";
import BusOperatorDashboard from "../busoperator/BusOperatorDashboard";
import AdminDashboard from "../admin/AdminDashboard";
import { UserProfilePage } from "../user/UserProfilePage";
import { BusOpProfile } from "../busoperator/BusOpProfile";
import AddBus from "../busoperator/AddBus";
import logo from "C:/reactjs/projectstructure/src/assets/S.png"
import AddTrip from "../busoperator/AddTrip";
import { SearchBus } from "../user/SearchBus";
import { BookingPage } from "../user/BookingPage";
import { ConfirmationPage } from "../user/ConfirmationPage";
import MyBookings from "../user/MyBookings";
import { useState,useEffect } from "react";
import { useNavigate } from "react-router-dom";
import BusOperatorBookings from "../busoperator/BusOperatorBookings";
import BusOperatorBuses from "../busoperator/BusOperatorBuses";
import { HomePage } from "./HomePage";
import BusOperatorPage from "../admin/BusOperatorPage";
import UserManagementPage from "../admin/UserManagementPage";
import AdminBookingsPage from "../admin/AdminBookingsPage";
import AddRoute from "../BusOperator/AddRoute";
import BusOperatorRoutes from "../busoperator/BusOperatorRoutes";
import BusOperatorTrips from "../busoperator/BusOperatorTrips";
import AdminRoutes from "../admin/AdminRoutes";
import AdminTrips from "../admin/AdminTrips";
import AdminBusManagement from "../admin/AdminBusManagement";
import BusOperatorApproval from "../admin/BusOperatorApproval";
import ForgotPasswordPage from "./ForgotPassword";

export function Routing() {
  const [username, setUsername] = useState(localStorage.getItem("username") || "");
  const [role, setRole] = useState(localStorage.getItem("role") || "");
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    setUsername("");
    setRole("");
    navigate("/login");
  };

  useEffect(() => {
    const updateStateFromStorage = () => {
      setUsername(localStorage.getItem("username") || "");
      setRole(localStorage.getItem("role") || "");
    };

    window.addEventListener("storageUpdated", updateStateFromStorage);
    updateStateFromStorage();

    return () => {
      window.removeEventListener("storageUpdated", updateStateFromStorage);
    };
  }, []);

  return (
    <>
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark fixed-top"style={{ backgroundColor: "#0d3b66" }}>
        <div className="container-fluid">
          <Link className="navbar-brand" to="/">SwiftBus <img
            src={logo}
            alt="SwiftBus Logo"
            style={{ height: "40px", width: "auto", marginRight: "8px" }}
          /></Link> 
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav ms-auto">
              {!username && (
                <>
                  <li className="nav-item">
                    <Link to="/register" className="nav-link">Register</Link>
                  </li>
                  <li className="nav-item">
                    <Link to="/login" className="nav-link">Login</Link>
                  </li>
                </>
              )}

              {username && (
                <>
                  <li className="nav-item nav-link text-white">
                    Hi, {username}
                  </li>
                  <li className="nav-item">
                    <button className="btn btn-outline-light btn-sm" onClick={handleLogout}>
                      Logout
                    </button>
                  </li>

                  {role === "user" && (
                    <li className="nav-item">
                      <Link to="/user/dashboard" className="nav-link">Dashboard</Link>
                    </li>
                  )}
                  {role === "busoperator" && (
                    <li className="nav-item">
                      <Link to="/busoperator/dashboard" className="nav-link">Dashboard</Link>
                    </li>
                  )}
                  {role === "admin" && (
                    <li className="nav-item">
                      <Link to="/admin/dashboard" className="nav-link">Dashboard</Link>
                    </li>
                  )}
                </>
              )}
            </ul>
          </div>
        </div>
      </nav>

      <div style={{ paddingTop: "70px", paddingBottom: "20px" }}>
        <Routes>
          <Route path="/" element={<HomePage />} /> 
          <Route path="/register" element={<RegistrationPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/user/dashboard" element={<UserDashboard />} />
          <Route path="/busoperator/dashboard" element={<BusOperatorDashboard />} />
          <Route path="/admin/dashboard" element={<AdminDashboard />} />
          <Route path="/userprofile" element={<UserProfilePage />} />
          <Route path="/busopprofile" element={<BusOpProfile />} />
          <Route path="/addbus" element={<AddBus />} />
          <Route path="/addroute" element={<AddRoute />} />
          <Route path="/addtrip" element={<AddTrip />} />
          <Route path="/searchbus" element={<SearchBus />} />
          <Route path="/booking" element={<BookingPage />} />
          <Route path="/confirmation" element={<ConfirmationPage />} />
          <Route path="/mybookings" element={<MyBookings />} />
          <Route path="/busoperator/bookings" element={<BusOperatorBookings />} />
          <Route path="/busoperator/buses" element={<BusOperatorBuses />} />
          <Route path="/bus-operators" element={<BusOperatorPage />} />
          <Route path="/usermanage" element={<UserManagementPage />} />
          <Route path="/adminbooking" element={<AdminBookingsPage />} />
          <Route path="/routebusop" element={<BusOperatorRoutes />} />
          <Route path="/tripbusop" element={<BusOperatorTrips />} />
          <Route path="/adminroute" element={<AdminRoutes />} />
          <Route path="/admintrip" element={<AdminTrips />} />
          <Route path="/adminbus" element={<AdminBusManagement />} />
          <Route path="/adminviewbusop" element={<BusOperatorPage />} />
          <Route path="/bus-operator-approvals" element={<BusOperatorApproval />} /> 
          <Route path="/forgot-password" element={<ForgotPasswordPage />} />
        </Routes>
      </div>
      
    </>
  );
}