// src/shared/components/layout/AppShell.tsx
import { makeStyles } from "@fluentui/react-components";
import type { ReactNode } from "react";
import { TopBar } from "./TopBar";
import { Sidebar } from "./Sidebar";

const useStyles = makeStyles({
  root: {
    display: "flex",
    flexDirection: "column",
    height: "100dvh", // dvh invece di vh: più affidabile su mobile con barre browser dinamiche
  },
  body: {
    display: "flex",
    flexDirection: "row",
    flex: 1,
    minHeight: 0, // FONDAMENTALE: senza questo, il contenuto sotto non scrolla mai correttamente
  },
  content: {
    flex: 1,
    overflow: "auto", // qui avviene lo scroll della pagina, non su <body>
  },
});

type Props = {
  children: ReactNode;
};

export function AppShell({ children }: Props) {
  const styles = useStyles();

  return (
    <div className={styles.root}>
      <TopBar />
      <div className={styles.body}>
        <Sidebar />
        <main className={styles.content}>{children}</main>
      </div>
    </div>
  );
}
