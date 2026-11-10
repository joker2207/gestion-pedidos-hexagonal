interface Props {
  mensaje: string | null;
}

export function ErrorBanner({ mensaje }: Props) {
  if (!mensaje) return null;
  return <div className="error">{mensaje}</div>;
}
