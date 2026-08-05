// Decodifica il payload di un JWT lato client, senza verificarne la firma
// (la verifica è responsabilità del backend). Usato per leggere claim non
// sensibili come l'email, in assenza di un endpoint "utente corrente".
export function decodeJwtPayload<T>(token: string): T | null {
  try {
    const payload = token.split(".")[1];
    const json = atob(payload.replace(/-/g, "+").replace(/_/g, "/"));
    return JSON.parse(json) as T;
  } catch {
    return null;
  }
}
