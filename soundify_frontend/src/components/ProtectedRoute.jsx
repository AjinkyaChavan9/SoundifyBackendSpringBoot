// ProtectedRoute.js
import React from "react";
import { Route, Navigate } from "react-router-dom";

function ProtectedRoute({ element, condition, redirectTo }) {
    return condition ? element : <Navigate to={redirectTo} />;
}

export default ProtectedRoute;
