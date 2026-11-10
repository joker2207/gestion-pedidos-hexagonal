export interface Cliente {
  id: number;
  nombre: string;
  email: string;
}

export interface Producto {
  id: number;
  nombre: string;
  precio: number;
  stock: number;
}

export type EstadoPedido = 'CREADO' | 'CONFIRMADO' | 'CANCELADO';

export interface LineaPedido {
  productoId: number;
  producto: string;
  precioUnitario: number;
  cantidad: number;
  subtotal: number;
}

export interface Pedido {
  id: number;
  clienteId: number;
  clienteNombre: string;
  estado: EstadoPedido;
  lineas: LineaPedido[];
  subtotal: number;
  descuento: number;
  total: number;
}

export interface NuevoCliente {
  nombre: string;
  email: string;
}

export interface NuevoProducto {
  nombre: string;
  precio: number;
  stock: number;
}

export interface NuevoItem {
  productoId: number;
  cantidad: number;
}

export interface NuevoPedido {
  clienteId: number;
  items: NuevoItem[];
}
