import { useCallback, useEffect, useState } from 'react';
import type { NuevoProducto, Producto } from '../domain/models';
import type { ProductoRepository } from '../domain/repositories';
import { productoRepository } from '../infrastructure/container';
import { mensajeDeError } from './errores';

export function useProductos(repositorio: ProductoRepository = productoRepository) {
  const [productos, setProductos] = useState<Producto[]>([]);
  const [error, setError] = useState<string | null>(null);

  const recargar = useCallback(async () => {
    try {
      setProductos(await repositorio.listar());
      setError(null);
    } catch (e) {
      setError(mensajeDeError(e));
    }
  }, [repositorio]);

  useEffect(() => {
    void recargar();
  }, [recargar]);

  const crear = useCallback(
    async (nuevo: NuevoProducto): Promise<boolean> => {
      try {
        await repositorio.crear(nuevo);
        await recargar();
        return true;
      } catch (e) {
        setError(mensajeDeError(e));
        return false;
      }
    },
    [repositorio, recargar],
  );

  return { productos, error, crear, recargar };
}
