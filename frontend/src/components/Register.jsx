import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { register } from "../services/authService";

function Register() {
  const navigate = useNavigate();

  const [auth, setAuth] = useState({
    name: "",
    password: "",
    email: "",
    phone: "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleChange = (e) => {
    setAuth({
      ...auth,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    try {
      await register(auth);
      navigate("/dashboard");
    } catch (error) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-green-50 via-white to-emerald-50 flex items-center justify-center px-4 py-10">

      {/* Register Card */}
      <div className="w-full max-w-md">

        {/* Logo / Heading */}
        <div className="text-center mb-6">
          <div className="inline-flex items-center justify-center w-16 h-16 rounded-full bg-green-100 mb-4">
            <span className="text-4xl">🌱</span>
          </div>

          <h1 className="text-3xl font-bold text-green-800">
            AgriSathi
          </h1>

          <p className="mt-1 text-gray-500">
            Smart Farmer Assistance & Crop Advisory
          </p>
        </div>

        {/* Form Card */}
        <div className="bg-white rounded-2xl shadow-xl border border-green-100 p-6 sm:p-8">

          <div className="mb-6">
            <h2 className="text-2xl font-bold text-gray-800">
              Create Account
            </h2>

            <p className="text-sm text-gray-500 mt-1">
              Join AgriSathi and get smart farming assistance.
            </p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-5">
            {error && (
              <p
                role="alert"
                className="rounded-lg border border-red-200 bg-red-50 px-3 py-2 text-sm text-red-700"
              >
                {error}
              </p>
            )}

            {/* Name */}
            <div>
              <label
                htmlFor="name"
                className="block text-sm font-semibold text-gray-700 mb-2"
              >
                Full Name
              </label>

              <input
                id="name"
                type="text"
                name="name"
                placeholder="Enter your full name"
                value={auth.name}
                onChange={handleChange}
                required
                className="w-full px-4 py-3 rounded-xl border border-gray-300
                           bg-gray-50 text-gray-800
                           placeholder-gray-400
                           focus:bg-white focus:outline-none
                           focus:ring-2 focus:ring-green-500
                           focus:border-green-500
                           transition"
              />
            </div>

            {/* Email */}
            <div>
              <label
                htmlFor="email"
                className="block text-sm font-semibold text-gray-700 mb-2"
              >
                Email Address
              </label>

              <input
                id="email"
                type="email"
                name="email"
                placeholder="Enter your email"
                value={auth.email}
                onChange={handleChange}
                required
                className="w-full px-4 py-3 rounded-xl border border-gray-300
                           bg-gray-50 text-gray-800
                           placeholder-gray-400
                           focus:bg-white focus:outline-none
                           focus:ring-2 focus:ring-green-500
                           focus:border-green-500
                           transition"
              />
            </div>

            {/* Password */}
            <div>
              <label
                htmlFor="password"
                className="block text-sm font-semibold text-gray-700 mb-2"
              >
                Password
              </label>

              <input
                id="password"
                type="password"
                name="password"
                placeholder="Create a password"
                value={auth.password}
                onChange={handleChange}
                required
                minLength={6}
                className="w-full px-4 py-3 rounded-xl border border-gray-300
                           bg-gray-50 text-gray-800
                           placeholder-gray-400
                           focus:bg-white focus:outline-none
                           focus:ring-2 focus:ring-green-500
                           focus:border-green-500
                           transition"
              />

              <p className="text-xs text-gray-400 mt-1">
                Password must be at least 6 characters.
              </p>
            </div>

            {/* Phone */}
            <div>
              <label
                htmlFor="phone"
                className="block text-sm font-semibold text-gray-700 mb-2"
              >
                Phone Number
              </label>

              <input
                id="phone"
                type="tel"
                name="phone"
                placeholder="Enter your phone number"
                value={auth.phone}
                onChange={handleChange}
                required
                className="w-full px-4 py-3 rounded-xl border border-gray-300
                           bg-gray-50 text-gray-800
                           placeholder-gray-400
                           focus:bg-white focus:outline-none
                           focus:ring-2 focus:ring-green-500
                           focus:border-green-500
                           transition"
              />
            </div>

            {/* Register Button */}
            <button
              type="submit"
              disabled={loading}
              className="w-full py-3.5 rounded-xl
                         bg-green-600 hover:bg-green-700
                         active:bg-green-800
                         text-white font-semibold
                         shadow-md hover:shadow-lg
                         transition duration-200
                         disabled:bg-gray-400
                         disabled:cursor-not-allowed"
            >
              {loading ? "Creating Account..." : "Create Account"}
            </button>
          </form>

          {/* Login */}
          <div className="mt-6 text-center">
            <p className="text-sm text-gray-500">
              Already have an account?{" "}
              <button
                type="button"
                onClick={() => navigate("/login")}
                className="font-semibold text-green-600 hover:text-green-700 hover:underline"
              >
                Login
              </button>
            </p>
          </div>
        </div>

        {/* Footer */}
        <p className="text-center text-xs text-gray-400 mt-6">
          🌾 Empowering farmers with smarter agricultural decisions
        </p>
      </div>
    </div>
  );
}

export default Register;