import { useMemo } from "react";
import { Navigate } from "react-router-dom";
import { getTokenPayload } from "../services/api";

function ProtectedRoute({ children }) {
  const isAuthenticated = useMemo(() => {
    try {
      getTokenPayload({ redirect: false });
      return true;
    } catch {
      return false;
    }
  }, []);

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return children;
}

export default ProtectedRoute;
