import type {
  Cliente,
  NuevoCliente,
  NuevoPedido,
  NuevoProducto,
  Pedido,
  Producto,
} from './models';

// Puertos: la aplicación depende de estas interfaces, no de fetch ni de HTTP.
export interface ClienteRepository {
  listar(): Promise<Cliente[]>;
  crear(cliente: NuevoCliente): Promise<Cliente>;
}

export interface ProductoRepository {
  listar(): Promise<Producto[]>;
  crear(producto: NuevoProducto): Promise<Producto>;
}

export interface PedidoRepository {
  listar(): Promise<Pedido[]>;
  crear(pedido: NuevoPedido): Promise<Pedido>;
  confirmar(id: number): Promise<Pedido>;
  cancelar(id: number): Promise<Pedido>;
}
