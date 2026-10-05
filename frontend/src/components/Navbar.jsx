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
        <div className="h-18 flex items-center justify-between">

          {/* Logo */}
          <Link to="/" className="flex items-center gap-2">
            <div className="w-11 h-11 rounded-full bg-green-100 flex items-center justify-center">
              <span className="text-2xl">🌱</span>
            </div>

            <div>
              <h1 className="text-xl font-bold text-green-800">
                AgriSathi
              </h1>

              <p className="hidden sm:block text-xs text-gray-500">
                Smart Farming Assistant
              </p>
            </div>
          </Link>

          {/* Navigation */}
          <div className="hidden items-center gap-5 md:flex">
            <Link to="/dashboard" className="font-medium text-gray-700 hover:text-green-700">Dashboard</Link>
            <Link to="/farms" className="font-medium text-gray-700 hover:text-green-700">Farms</Link>
            <Link to="/crops" className="font-medium text-gray-700 hover:text-green-700">Crops</Link>
            <Link to="/weather" className="font-medium text-gray-700 hover:text-green-700">Weather</Link>
            <Link to="/ai-advice" className="font-medium text-gray-700 hover:text-green-700">AI Advice</Link>
          </div>

          {/* Auth Buttons */}
          <div className="flex items-center gap-3">
            <button
              onClick={handleLogout}
              className="rounded-lg border border-red-200 px-4 py-2 font-semibold text-red-600 transition hover:bg-red-50"
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