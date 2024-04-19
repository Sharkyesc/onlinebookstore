import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomePage from "../pages/home";
import BookPage from "../pages/books";
import CartPage from "../pages/cart";
import UserPage from "../pages/user";
/*
import LoginPage from "../page/login";
import OrderPage from "../page/order";*/

export default function AppRouter() {
    return <BrowserRouter>
        <Routes>
            <Route index element={<HomePage />} />            
            <Route path="/book/:id" element={<BookPage />} />
            <Route path="/cart" element={<CartPage />} />
            <Route path="/user" element={<UserPage />} />
            <Route path="/*" element={<HomePage />} />
        </Routes>
    </BrowserRouter>
}