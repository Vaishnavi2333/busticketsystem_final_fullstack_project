import { useNavigate } from "react-router";

import {
  FaUserCheck,
  FaUsers,
  FaBus,
  FaCalendarCheck,
  FaMapMarkedAlt,
  FaRoute,
  FaTruck
} from "react-icons/fa";

export default function AdminDashboard() {
  const navigate = useNavigate();

  const dashboardItems = [
    { label: "Bus Operator Requests", path: "/bus-operator-approvals", icon: <FaUserCheck size={40} color="#ff6b6b" /> },
    { label: "Manage Bus Operators", path: "/adminviewbusop", icon: <FaBus size={40} color="#339af0" /> },
    { label: "Manage Users", path: "/usermanage", icon: <FaUsers size={40} color="#51cf66" /> },
    { label: "Manage Bookings", path: "/adminbooking", icon: <FaCalendarCheck size={40} color="#845ef7" /> },
    { label: "Manage Routes", path: "/adminroute", icon: <FaMapMarkedAlt size={40} color="#fcc419" /> },
    { label: "Manage Trips", path: "/admintrip", icon: <FaRoute size={40} color="#228be6" /> },
    { label: "Manage Bus", path: "/adminbus", icon: <FaTruck size={40} color="#fd7e14" /> },
  ];

  return (
    <div className="container my-5">
      <h2 className="text-center mb-5">Admin Dashboard</h2>

      <div className="row g-4">
        {dashboardItems.map((item, index) => (
          <div
            key={index}
            className={`col-12 col-md-6 col-lg-4 ${index === dashboardItems.length - 1 ? "mx-auto" : ""}`}
          >
            <div
              className="card shadow-sm h-100 text-center p-4"
              style={{ cursor: "pointer", transition: "transform 0.2s" }}
              onClick={() => navigate(item.path)}
              onMouseEnter={(e) => (e.currentTarget.style.transform = "scale(1.05)")}
              onMouseLeave={(e) => (e.currentTarget.style.transform = "scale(1)")}
            >
              <div className="mb-3">{item.icon}</div>
              <h5 className="card-title">{item.label}</h5>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
