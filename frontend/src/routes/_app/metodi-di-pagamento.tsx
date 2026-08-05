import { createFileRoute } from "@tanstack/react-router";

export const Route = createFileRoute("/_app/metodi-di-pagamento")({
  component: () => <div>Metodi di pagamento</div>,
});
