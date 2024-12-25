import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomePage from "../pages/home";
import BookPage from "../pages/books";
import CartPage from "../pages/cart";
import UserPage from "../pages/user";
import LoginPage from "../pages/login";
import OrderPage from "../pages/order";
import RegisterPage from "../pages/register";
import BookListPage from "../pages/booklist";
import StatisticsPage from "../pages/statistics";
import UserManagementPage from "../pages/manage";
import AdminStatisticsPage from "../pages/adminStat";
import BookManagePage from "../pages/managebook";
import NotFoundPage from "../pages/notFoundBook";
import BookAuthorSearchPage from "../pages/authorSearch";
import TagSearchPage from "../pages/tagSearch";
import BookByTitlePage from "../pages/bookByTitlePage";
import ProtectedRoute from "./protectedRoute"

export default function AppRouter() {
    console.log(sessionStorage.getItem('username'));
    return <BrowserRouter>
        <Routes>
            <Route index element={<LoginPage />} />
            <Route path="/home" element={<ProtectedRoute component={HomePage} />} />   
            <Route path="/book/:id" element={<ProtectedRoute component={BookPage} />} />
            <Route path="/cart" element={<ProtectedRoute component={CartPage} />} />
            <Route path="/user" element={<ProtectedRoute component={UserPage} />} />
            <Route path="/order" element={<ProtectedRoute component={OrderPage} />} />
            <Route path="/book" element={<ProtectedRoute component={BookListPage} />} />
            <Route path="/bookManage" element={<ProtectedRoute component={BookManagePage} />} /> 
            <Route path="/userstatistics" element={<ProtectedRoute component={StatisticsPage} />} /> 
            <Route path="/manage" element={<ProtectedRoute component={UserManagementPage} />} />
            <Route path="/statistics" element={<ProtectedRoute component={AdminStatisticsPage} />} /> 
            <Route path="/book/notfound" element={<ProtectedRoute component={NotFoundPage} />} /> 
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/author" element={<BookAuthorSearchPage />} />
            <Route path="/tag" element={<TagSearchPage />} />
            <Route path="/bookbytitle" element={<BookByTitlePage />} />
        </Routes>
    </BrowserRouter>
}