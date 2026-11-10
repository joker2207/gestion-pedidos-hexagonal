import type { Cliente, NuevoPedido, Pedido, Producto } from '../../domain/models';
import { ErrorBanner } from '../components/ErrorBanner';
import { PedidoCard } from '../components/PedidoCard';
import { PedidoForm } from '../components/PedidoForm';

interface Props {
  pedidos: Pedido[];
  clientes: Cliente[];
  productos: Producto[];
  error: string | null;
  onCrear: (pedido: NuevoPedido) => Promise<boolean>;
  onConfirmar: (id: number) => void;
  onCancelar: (id: number) => void;
}

export function PedidosPage({
  pedidos,
  clientes,
  productos,
  error,
  onCrear,
  onConfirmar,
  onCancelar,
}: Props) {
  return (
    <section>
      <ErrorBanner mensaje={error} />
      <PedidoForm clientes={clientes} productos={productos} onCrear={onCrear} />

      {pedidos.length === 0 && <p className="vacio">Aún no hay pedidos.</p>}
      {pedidos.map((pedido) => (
        <PedidoCard
          key={pedido.id}
          pedido={pedido}
          onConfirmar={onConfirmar}
          onCancelar={onCancelar}
        />
      ))}
    </section>
  );
}
