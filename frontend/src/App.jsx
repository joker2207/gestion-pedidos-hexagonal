import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [clienteId, setClienteId] = useState('');
  const [nombreProducto, setNombreProducto] = useState('');
  const [cantidad, setCantidad] = useState(1);
  const [precioUnitario, setPrecioUnitario] = useState(0);
  const [pedidoResultado, setPedidoResultado] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const payload = {
      clienteId: clienteId,
      items: [
        {
          productoId: "PROD-" + Math.floor(Math.random() * 1000),
          nombreProducto: nombreProducto,
          cantidad: parseInt(cantidad),
          precioUnitario: parseFloat(precioUnitario)
        }
      ]
    };

    try {
      const response = await axios.post('http://localhost:8080/api/pedidos', payload);
      setPedidoResultado(response.data);
      alert('¡Pedido creado exitosamente!');
    } catch (error) {
      console.error('Error al crear el pedido:', error);
      alert('Error al conectar con el backend.');
    }
  };

  return (
    <div className="container">
      <h1>Gestión de Pedidos</h1>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>ID Cliente</label>
          <input 
            type="text" 
            placeholder="Ej: CLI-001"
            value={clienteId} 
            onChange={(e) => setClienteId(e.target.value)} 
            required 
          />
        </div>

        <div className="form-group">
          <label>Producto</label>
          <input 
            type="text" 
            placeholder="Ej: Teclado Mecánico"
            value={nombreProducto} 
            onChange={(e) => setNombreProducto(e.target.value)} 
            required 
          />
        </div>

        <div className="form-group">
          <label>Cantidad</label>
          <input 
            type="number" 
            min="1"
            value={cantidad} 
            onChange={(e) => setCantidad(e.target.value)} 
            required 
          />
        </div>

        <div className="form-group">
          <label>Precio Unitario ($)</label>
          <input 
            type="number" 
            step="0.01"
            min="0.01"
            placeholder="0.00"
            value={precioUnitario} 
            onChange={(e) => setPrecioUnitario(e.target.value)} 
            required 
          />
        </div>

        <button type="submit" className="btn-submit">Crear Pedido</button>
      </form>

      {pedidoResultado && (
        <div className="result-card">
          <h3>✅ Pedido Registrado Con Éxito</h3>
          <p><strong>ID Pedido:</strong> {pedidoResultado.id}</p>
          <p><strong>Estado:</strong> {pedidoResultado.estado}</p>
          <p><strong>Total Calulado:</strong> ${pedidoResultado.total}</p>
        </div>
      )}
    </div>
  );
}

export default App;