import { useCallback, useEffect, useState } from 'react';
import type { Cliente, NuevoCliente } from '../domain/models';
import type { ClienteRepository } from '../domain/repositories';
import { clienteRepository } from '../infrastructure/container';
import { mensajeDeError } from './errores';

export function useClientes(repositorio: ClienteRepository = clienteRepository) {
  const [clientes, setClientes] = useState<Cliente[]>([]);
  const [error, setError] = useState<string | null>(null);

  const recargar = useCallback(async () => {
    try {
      setClientes(await repositorio.listar());
      setError(null);
    } catch (e) {
      setError(mensajeDeError(e));
    }
  }, [repositorio]);

  useEffect(() => {
    void recargar();
  }, [recargar]);

  const crear = useCallback(
    async (nuevo: NuevoCliente): Promise<boolean> => {
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

  return { clientes, error, crear, recargar };
}
