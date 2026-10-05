import { Navigate, Route, Routes } from "react-router-dom";
import AppLayout from "./components/AppLayout";
import ProtectedRoute from "./components/ProtectedRoute";
import Login from "./pages/auth/Login";
import Register from "./pages/auth/Register";
import FarmList from "./pages/farm/FarmList";
import AddFarm from "./pages/farm/AddFarm";
import FarmDetails from "./pages/farm/FarmDetails";
import EditFarm from "./pages/farm/EditFarm";
import CropList from "./pages/crop/CropList";
import AddCrop from "./pages/crop/AddCrop";
import CropDetails from "./pages/crop/CropDetails";
import EditCrop from "./pages/crop/EditCrop";
import CropAdvisory from "./pages/crop/CropAdvisory";
import Weather from "./pages/weather/Weather";
import AIAdvice from "./pages/ai/AIAdvice";
import DiseaseDetection from "./pages/ai/DiseaseDetection";
import AIChat from "./pages/ai/AIChat";
import Dashboard from "./pages/dashboard/Dashboard";
import Home from "./pages/Home";
import "./index.css";

function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <Home />
          </ProtectedRoute>
        }
      />
      <Route
        element={
          <ProtectedRoute>
            <AppLayout />
          </ProtectedRoute>
        }
      >
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/farms" element={<FarmList />} />
        <Route path="/farms/new" element={<AddFarm />} />
        <Route path="/farms/:farmId" element={<FarmDetails />} />
        <Route path="/farms/:farmId/edit" element={<EditFarm />} />
        <Route path="/crops" element={<CropList />} />
        <Route path="/crops/new" element={<AddCrop />} />
        <Route path="/crops/:cropId/edit" element={<EditCrop />} />
        <Route path="/crops/:cropId" element={<CropDetails />} />
        <Route path="/crop-advisory" element={<CropAdvisory />} />
        <Route path="/weather" element={<Weather />} />
        <Route path="/ai-advice" element={<AIAdvice />} />
        <Route path="/disease-detection" element={<DiseaseDetection />} />
        <Route path="/ai-chat" element={<AIChat />} />
      </Route>
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}

export default App;
