import { useState } from 'react';
import { useClientes } from './application/useClientes';
import { usePedidos } from './application/usePedidos';
import { useProductos } from './application/useProductos';
import { ClientesPage } from './ui/pages/ClientesPage';
import { PedidosPage } from './ui/pages/PedidosPage';
import { ProductosPage } from './ui/pages/ProductosPage';

type Pestana = 'pedidos' | 'productos' | 'clientes';

export default function App() {
  const [pestana, setPestana] = useState<Pestana>('pedidos');
  const clientes = useClientes();
  const productos = useProductos();
  const pedidos = usePedidos();

  // Confirmar o cancelar cambia el stock, así que se recargan los productos.
  const confirmar = async (id: number) => {
    await pedidos.confirmar(id);
    await productos.recargar();
  };
  const cancelar = async (id: number) => {
    await pedidos.cancelar(id);
    await productos.recargar();
  };

  return (
    <main className="app">
      <h1>Gestión de pedidos</h1>

      <nav className="tabs">
        {(['pedidos', 'productos', 'clientes'] as Pestana[]).map((nombre) => (
          <button
            key={nombre}
            className={pestana === nombre ? 'activa' : ''}
            onClick={() => setPestana(nombre)}
          >
            {nombre}
          </button>
        ))}
      </nav>

      {pestana === 'pedidos' && (
        <PedidosPage
          pedidos={pedidos.pedidos}
          clientes={clientes.clientes}
          productos={productos.productos}
          error={pedidos.error}
          onCrear={pedidos.crear}
          onConfirmar={confirmar}
          onCancelar={cancelar}
        />
      )}
      {pestana === 'productos' && (
        <ProductosPage productos={productos.productos} error={productos.error} onCrear={productos.crear} />
      )}
      {pestana === 'clientes' && (
        <ClientesPage clientes={clientes.clientes} error={clientes.error} onCrear={clientes.crear} />
      )}
    </main>
  );
}
