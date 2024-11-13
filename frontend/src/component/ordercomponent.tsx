import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import orderService from '../service/order-service.tsx';

const OrderComponent = () => {
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        orderService.getAllOrders().then((response) => {
            setOrders(response.data);
        });
    }, []);

    return (
        <div className="container mt-5">
            <h2>Orders</h2>
            <ul className="list-group">
                {orders.map(order => (
                    <li key={order.id} className="list-group-item">
                        {order.product} - {order.quantity}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default OrderComponent;