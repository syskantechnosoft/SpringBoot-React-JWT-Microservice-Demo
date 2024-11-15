import React, { useEffect, useState } from 'react';
import ProductService from '../service/product-service.tsx';
import 'bootstrap/dist/css/bootstrap.min.css';

const ProductComponent = () => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        ProductService.getAllProducts().then((response) => {
            setProducts(response.data);
        });
    }, []);

    return (
        <div className="container mt-5">
            <h2>Products</h2>
            <ul className="list-group">
                {products.map(product => (
                    <li key={product.id} className="list-group-item">
                        {product.name} - ${product.price}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ProductComponent;