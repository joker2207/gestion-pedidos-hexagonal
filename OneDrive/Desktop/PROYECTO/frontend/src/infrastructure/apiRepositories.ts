import type {
  Cliente,
  NuevoCliente,
  NuevoPedido,
  NuevoProducto,
  Pedido,
  Producto,
} from '../domain/models';
import type {
  ClienteRepository,
  PedidoRepository,
  ProductoRepository,
} from '../domain/repositories';
import { post, request } from './httpClient';

export class ApiClienteRepository implements ClienteRepository {
  listar(): Promise<Cliente[]> {
    return request<Cliente[]>('/api/clientes');
  }

  crear(cliente: NuevoCliente): Promise<Cliente> {
    return post<Cliente>('/api/clientes', cliente);
  }
}

export class ApiProductoRepository implements ProductoRepository {
  listar(): Promise<Producto[]> {
    return request<Producto[]>('/api/productos');
  }

  crear(producto: NuevoProducto): Promise<Producto> {
    return post<Producto>('/api/productos', producto);
  }
}

export class ApiPedidoRepository implements PedidoRepository {
  listar(): Promise<Pedido[]> {
    return request<Pedido[]>('/api/pedidos');
  }

  crear(pedido: NuevoPedido): Promise<Pedido> {
    return post<Pedido>('/api/pedidos', pedido);
  }

  confirmar(id: number): Promise<Pedido> {
    return post<Pedido>(`/api/pedidos/${id}/confirmar`);
  }

  cancelar(id: number): Promise<Pedido> {
    return post<Pedido>(`/api/pedidos/${id}/cancelar`);
  }
}
