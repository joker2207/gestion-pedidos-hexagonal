const formatoMoneda = new Intl.NumberFormat('es-CO', {
  style: 'currency',
  currency: 'COP',
  maximumFractionDigits: 0,
});

export function moneda(valor: number): string {
  return formatoMoneda.format(valor);
}
