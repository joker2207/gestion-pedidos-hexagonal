import {
  ApiClienteRepository,
  ApiPedidoRepository,
  ApiProductoRepository,
} from './apiRepositories';

// Único lugar donde se eligen las implementaciones concretas.
export const clienteRepository = new ApiClienteRepository();
export const productoRepository = new ApiProductoRepository();
export const pedidoRepository = new ApiPedidoRepository();
