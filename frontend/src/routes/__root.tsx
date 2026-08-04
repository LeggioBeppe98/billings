// routes/__root.tsx
import { Outlet, createRootRoute } from "@tanstack/react-router";
import { TanStackRouterDevtools } from "@tanstack/router-devtools";

export const Route = createRootRoute({
  component: RootComponent,
});

function RootComponent() {
  return (
    <>
      {/* Navbar/Header globale (se esiste) */}
      {/* <Header /> */}

      <Outlet />

      {/* Devtools solo in development */}
      {import.meta.env.DEV && <TanStackRouterDevtools />}
    </>
  );
}
