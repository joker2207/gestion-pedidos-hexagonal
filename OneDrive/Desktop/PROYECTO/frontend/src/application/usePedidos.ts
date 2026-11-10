import { useCallback, useEffect, useState } from 'react';
import type { NuevoPedido, Pedido } from '../domain/models';
import type { PedidoRepository } from '../domain/repositories';
import { pedidoRepository } from '../infrastructure/container';
import { mensajeDeError } from './errores';

export function usePedidos(repositorio: PedidoRepository = pedidoRepository) {
  const [pedidos, setPedidos] = useState<Pedido[]>([]);
  const [error, setError] = useState<string | null>(null);

  const recargar = useCallback(async () => {
    try {
      setPedidos(await repositorio.listar());
      setError(null);
    } catch (e) {
      setError(mensajeDeError(e));
    }
  }, [repositorio]);

  useEffect(() => {
    void recargar();
  }, [recargar]);

  const ejecutar = useCallback(
    async (accion: () => Promise<unknown>): Promise<boolean> => {
      try {
        await accion();
        await recargar();
        return true;
      } catch (e) {
        setError(mensajeDeError(e));
        return false;
      }
    },
    [recargar],
  );

  const crear = useCallback(
    (nuevo: NuevoPedido) => ejecutar(() => repositorio.crear(nuevo)),
    [repositorio, ejecutar],
  );
  const confirmar = useCallback(
    (id: number) => ejecutar(() => repositorio.confirmar(id)),
    [repositorio, ejecutar],
  );
  const cancelar = useCallback(
    (id: number) => ejecutar(() => repositorio.cancelar(id)),
    [repositorio, ejecutar],
  );

  return { pedidos, error, crear, confirmar, cancelar, recargar };
}
