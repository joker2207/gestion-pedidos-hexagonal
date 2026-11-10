import type { Pedido } from '../../domain/models';
import { moneda } from '../format';

interface Props {
  pedido: Pedido;
  onConfirmar: (id: number) => void;
  onCancelar: (id: number) => void;
}

export function PedidoCard({ pedido, onConfirmar, onCancelar }: Props) {
  const puedeConfirmar = pedido.estado === 'CREADO';
  const puedeCancelar = pedido.estado !== 'CANCELADO';

  return (
    <article className="card">
      <header className="card-header">
        <h3>
          Pedido #{pedido.id} · {pedido.clienteNombre}
        </h3>
        <span className={`badge ${pedido.estado.toLowerCase()}`}>{pedido.estado}</span>
      </header>

      <table>
        <thead>
          <tr>
            <th>Producto</th>
            <th>Cant.</th>
            <th>Precio</th>
            <th>Subtotal</th>
          </tr>
        </thead>
        <tbody>
          {pedido.lineas.map((linea, indice) => (
            <tr key={`${linea.productoId}-${indice}`}>
              <td>{linea.producto}</td>
              <td>{linea.cantidad}</td>
              <td>{moneda(linea.precioUnitario)}</td>
              <td>{moneda(linea.subtotal)}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="totales">
        <span>Subtotal: {moneda(pedido.subtotal)}</span>
        <span>Descuento: {moneda(pedido.descuento)}</span>
        <strong>Total: {moneda(pedido.total)}</strong>
      </div>

      <div className="acciones">
        {puedeConfirmar && (
          <button onClick={() => onConfirmar(pedido.id)}>Confirmar</button>
        )}
        {puedeCancelar && (
          <button className="secundario" onClick={() => onCancelar(pedido.id)}>
            Cancelar
          </button>
        )}
      </div>
    </article>
  );
}
