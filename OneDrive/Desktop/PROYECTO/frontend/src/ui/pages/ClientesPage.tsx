import { useState, type FormEvent } from 'react';
import type { Cliente, NuevoCliente } from '../../domain/models';
import { ErrorBanner } from '../components/ErrorBanner';

interface Props {
  clientes: Cliente[];
  error: string | null;
  onCrear: (cliente: NuevoCliente) => Promise<boolean>;
}

export function ClientesPage({ clientes, error, onCrear }: Props) {
  const [nombre, setNombre] = useState('');
  const [email, setEmail] = useState('');

  const enviar = async (evento: FormEvent) => {
    evento.preventDefault();
    const creado = await onCrear({ nombre, email });
    if (creado) {
      setNombre('');
      setEmail('');
    }
  };

  return (
    <section>
      <ErrorBanner mensaje={error} />

      <form className="card form" onSubmit={enviar}>
        <h3>Nuevo cliente</h3>
        <div className="fila">
          <input placeholder="Nombre" value={nombre} onChange={(e) => setNombre(e.target.value)} required />
          <input
            type="email"
            placeholder="Correo"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <button type="submit">Guardar</button>
        </div>
      </form>

      <div className="card">
        <table>
          <thead>
            <tr>
              <th>Nombre</th>
              <th>Correo</th>
            </tr>
          </thead>
          <tbody>
            {clientes.map((cliente) => (
              <tr key={cliente.id}>
                <td>{cliente.nombre}</td>
                <td>{cliente.email}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </section>
  );
}
