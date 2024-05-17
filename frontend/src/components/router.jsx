import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomePage from "../pages/home";
import BookPage from "../pages/books";
import CartPage from "../pages/cart";
import UserPage from "../pages/user";
import LoginPage from "../pages/login";
import OrderPage from "../pages/order";
import RegisterPage from "../pages/register";
import ProtectedRoute from "./protectedRoute"

export default function AppRouter() {
    return <BrowserRouter>
        <Routes>
            <Route index element={<LoginPage />} />
            <Route path="/home" element={<ProtectedRoute component={HomePage} />} />   
            <Route path="/book/:id" element={<ProtectedRoute component={BookPage} />} />
            <Route path="/cart" element={<ProtectedRoute component={CartPage} />} />
            <Route path="/user" element={<ProtectedRoute component={UserPage} />} />
            <Route path="/order" element={<ProtectedRoute component={OrderPage} />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/login" element={<LoginPage />} />
        </Routes>
    </BrowserRouter>
}