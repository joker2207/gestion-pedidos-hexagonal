import { useState, type FormEvent } from 'react';
import type { NuevoProducto, Producto } from '../../domain/models';
import { ErrorBanner } from '../components/ErrorBanner';
import { moneda } from '../format';

interface Props {
  productos: Producto[];
  error: string | null;
  onCrear: (producto: NuevoProducto) => Promise<boolean>;
}

export function ProductosPage({ productos, error, onCrear }: Props) {
  const [nombre, setNombre] = useState('');
  const [precio, setPrecio] = useState('');
  const [stock, setStock] = useState('');

  const enviar = async (evento: FormEvent) => {
    evento.preventDefault();
    const creado = await onCrear({ nombre, precio: Number(precio), stock: Number(stock) });
    if (creado) {
      setNombre('');
      setPrecio('');
      setStock('');
    }
  };

  return (
    <section>
      <ErrorBanner mensaje={error} />

      <form className="card form" onSubmit={enviar}>
        <h3>Nuevo producto</h3>
        <div className="fila">
          <input placeholder="Nombre" value={nombre} onChange={(e) => setNombre(e.target.value)} required />
          <input
            type="number"
            min={1}
            placeholder="Precio"
            value={precio}
            onChange={(e) => setPrecio(e.target.value)}
            required
          />
          <input
            type="number"
            min={0}
            placeholder="Stock"
            value={stock}
            onChange={(e) => setStock(e.target.value)}
            required
          />
          <button type="submit">Guardar</button>
        </div>
      </form>

      <div className="card">
        <table>
          <thead>
            <tr>
              <th>Producto</th>
              <th>Precio</th>
              <th>Stock</th>
            </tr>
          </thead>
          <tbody>
            {productos.map((producto) => (
              <tr key={producto.id}>
                <td>{producto.nombre}</td>
                <td>{moneda(producto.precio)}</td>
                <td>{producto.stock}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </section>
  );
}
