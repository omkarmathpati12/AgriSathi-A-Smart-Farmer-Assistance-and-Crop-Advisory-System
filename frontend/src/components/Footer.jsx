import { Link } from "react-router-dom";

function Footer() {
  return (
    <footer className="border-t border-green-100 bg-white px-4 py-6">
      <div className="mx-auto flex max-w-6xl flex-col items-center justify-between gap-4 sm:flex-row">
        <div className="text-center sm:text-left">
          <p className="font-bold text-green-900">AgriSathi 🌱</p>
          <p className="mt-1 text-sm text-gray-500">
            Practical tools for a healthy and productive farm.
          </p>
        </div>
        <nav aria-label="Footer navigation" className="flex flex-wrap justify-center gap-x-4 gap-y-2 text-sm">
          <Link to="/dashboard" className="text-gray-600 hover:text-green-800">Dashboard</Link>
          <Link to="/farms" className="text-gray-600 hover:text-green-800">Farms</Link>
          <Link to="/crops" className="text-gray-600 hover:text-green-800">Crops</Link>
          <Link to="/weather" className="text-gray-600 hover:text-green-800">Weather</Link>
        </nav>
      </div>
    </footer>
  );
}

export default Footer;
