import { createFileRoute } from "@tanstack/react-router";
import logo from "@/assets/logo.svg";

export const Route = createFileRoute("/_app/home")({
  component: () => (
    <div
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        height: "100%",
      }}
    >
      <img
        src={logo}
        alt=""
        style={{ width: "40%", maxWidth: "360px", opacity: 0.08 }}
      />
    </div>
  ),
});
