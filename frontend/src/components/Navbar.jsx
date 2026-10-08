import { Link, useNavigate } from "react-router-dom";
import { logout } from "../services/authService";

function Navbar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login", { replace: true });
  };

  return (
      <nav className="sticky top-0 z-50 bg-white/95 backdrop-blur-md border-b border-green-100 shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="h-16 flex items-center justify-between">

            {/* ================= LOGO ================= */}
            <Link
                to="/"
                className="flex items-center gap-3 group"
            >
              <div className="w-11 h-11 rounded-xl bg-green-100 flex items-center justify-center
                            group-hover:bg-green-200 transition">
                <span className="text-2xl">🌱</span>
              </div>

              <div>
                <h1 className="text-xl font-extrabold text-green-800 tracking-tight">
                  AgriSathi
                </h1>

                <p className="hidden sm:block text-xs text-gray-500">
                  Smart Farming Assistant
                </p>
              </div>
            </Link>

            {/* ================= NAVIGATION ================= */}
            <div className="hidden md:flex items-center gap-6">
              <Link
                  to="/dashboard"
                  className="font-medium text-gray-600 hover:text-green-700 transition"
              >
                Dashboard
              </Link>

              <Link
                  to="/farms"
                  className="font-medium text-gray-600 hover:text-green-700 transition"
              >
                Farms
              </Link>

              <Link
                  to="/crops"
                  className="font-medium text-gray-600 hover:text-green-700 transition"
              >
                Crops
              </Link>

              <Link
                  to="/weather"
                  className="font-medium text-gray-600 hover:text-green-700 transition"
              >
                Weather
              </Link>

              <Link
                  to="/ai-advice"
                  className="font-medium text-gray-600 hover:text-green-700 transition"
              >
                AI Advice
              </Link>
            </div>

            {/* ================= AUTH BUTTONS ================= */}
            <div className="flex items-center gap-2">

              {/* Login */}
              <Link
                  to="/login"
                  className="hidden sm:inline-flex items-center justify-center
                         px-4 py-2 rounded-lg
                         border border-green-200
                         text-green-700 font-semibold
                         hover:bg-green-50
                         transition"
              >
                Login
              </Link>

              {/* Logout */}
              <button
                  onClick={handleLogout}
                  className="inline-flex items-center justify-center
                         px-4 py-2 rounded-lg
                         bg-red-50
                         border border-red-200
                         text-red-600 font-semibold
                         hover:bg-red-100
                         transition"
              >
                Logout
              </button>
            </div>

          </div>
        </div>
      </nav>
  );
}

export default Navbar;