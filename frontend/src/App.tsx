// import logo from './logo.svg';
// import './App.css';

import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import ProductComponent from './component/productcomponent.tsx';
import OrderComponent from './component/ordercomponent.tsx';

const App = () => {
    return (
        <Router>
            <div className="container">
                <Routes>
                    <Route path="/" element={<ProductComponent />} />
                    <Route path="/products" element={<ProductComponent />} />
                    <Route path="/orders" element={<OrderComponent />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;
