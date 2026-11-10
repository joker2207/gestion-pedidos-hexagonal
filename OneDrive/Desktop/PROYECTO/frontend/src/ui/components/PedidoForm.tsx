import { useState, type FormEvent } from 'react';
import type { Cliente, NuevoItem, NuevoPedido, Producto } from '../../domain/models';

interface Props {
  clientes: Cliente[];
  productos: Producto[];
  onCrear: (pedido: NuevoPedido) => Promise<boolean>;
}

const LINEA_VACIA = { productoId: '', cantidad: 1 };

export function PedidoForm({ clientes, productos, onCrear }: Props) {
  const [clienteId, setClienteId] = useState('');
  const [lineas, setLineas] = useState([{ ...LINEA_VACIA }]);

  const actualizarLinea = (indice: number, cambios: Partial<typeof LINEA_VACIA>) =>
    setLineas((actuales) =>
      actuales.map((linea, i) => (i === indice ? { ...linea, ...cambios } : linea)),
    );

  const quitarLinea = (indice: number) =>
    setLineas((actuales) => actuales.filter((_, i) => i !== indice));

  const enviar = async (evento: FormEvent) => {
    evento.preventDefault();
    const items: NuevoItem[] = lineas
      .filter((linea) => linea.productoId !== '')
      .map((linea) => ({ productoId: Number(linea.productoId), cantidad: linea.cantidad }));

    if (clienteId === '' || items.length === 0) return;

    const creado = await onCrear({ clienteId: Number(clienteId), items });
    if (creado) {
      setClienteId('');
      setLineas([{ ...LINEA_VACIA }]);
    }
  };

  return (
    <form className="card form" onSubmit={enviar}>
      <h3>Nuevo pedido</h3>

      <label>
        Cliente
        <select value={clienteId} onChange={(e) => setClienteId(e.target.value)} required>
          <option value="">Selecciona un cliente</option>
          {clientes.map((cliente) => (
            <option key={cliente.id} value={cliente.id}>
              {cliente.nombre}
            </option>
          ))}
        </select>
      </label>

      {lineas.map((linea, indice) => (
        <div className="fila" key={indice}>
          <select
            value={linea.productoId}
            onChange={(e) => actualizarLinea(indice, { productoId: e.target.value })}
            required
          >
            <option value="">Producto</option>
            {productos.map((producto) => (
              <option key={producto.id} value={producto.id}>
                {producto.nombre} (stock {producto.stock})
              </option>
            ))}
          </select>
          <input
            type="number"
            min={1}
            value={linea.cantidad}
            onChange={(e) => actualizarLinea(indice, { cantidad: Number(e.target.value) })}
          />
          {lineas.length > 1 && (
            <button type="button" className="secundario" onClick={() => quitarLinea(indice)}>
              Quitar
            </button>
          )}
        </div>
      ))}

      <div className="acciones">
        <button
          type="button"
          className="secundario"
          onClick={() => setLineas((actuales) => [...actuales, { ...LINEA_VACIA }])}
        >
          + Agregar producto
        </button>
        <button type="submit">Crear pedido</button>
      </div>
    </form>
  );
}
