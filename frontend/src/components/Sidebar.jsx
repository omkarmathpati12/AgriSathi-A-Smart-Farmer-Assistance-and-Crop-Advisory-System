import { NavLink } from "react-router-dom";

const links = [
  ["/dashboard", "Dashboard", "🏠"],
  ["/farms", "My farms", "🚜"],
  ["/crops", "My crops", "🌾"],
  ["/crop-advisory", "Crop advisory", "🌱"],
  ["/weather", "Weather", "🌤️"],
  ["/ai-advice", "AI advice", "💡"],
  ["/disease-detection", "Disease detection", "🔎"],
  ["/ai-chat", "Ask AgriSathi", "💬"],
];

function Sidebar() {
  return (
    <aside className="border-b border-green-100 bg-white p-3 lg:min-h-full lg:w-60 lg:border-b-0 lg:border-r lg:p-4">
      <nav aria-label="Main navigation" className="flex gap-2 overflow-x-auto lg:flex-col">
        {links.map(([path, label, icon]) => (
          <NavLink
            key={path}
            to={path}
            className={({ isActive }) =>
              `whitespace-nowrap rounded-lg px-3 py-2 text-sm font-medium transition ${
                isActive
                  ? "bg-green-100 text-green-900"
                  : "text-gray-600 hover:bg-green-50 hover:text-green-900"
              }`
            }
          >
            <span className="mr-2" aria-hidden="true">{icon}</span>
            {label}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
}

export default Sidebar;
