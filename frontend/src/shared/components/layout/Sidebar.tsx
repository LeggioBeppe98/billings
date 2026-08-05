// src/shared/components/layout/Sidebar.tsx
import { Button, makeStyles, mergeClasses, tokens } from "@fluentui/react-components";
import { NavigationRegular } from "@fluentui/react-icons";
import { Link } from "@tanstack/react-router";
import { useState } from "react";
import { APP_SECTION_LIST } from "./app-sections";

const SIDEBAR_WIDTH_EXPANDED = "240px";
const SIDEBAR_WIDTH_COLLAPSED = "52px"; // solo icona + un po' di padding

const useStyles = makeStyles({
  root: {
    display: "flex",
    flexDirection: "column",
    flexShrink: 0,
    height: "100%",
    overflow: "hidden",
    transition: "width 0.2s ease", // anima il collasso invece di uno scatto secco
    backgroundColor: tokens.colorNeutralBackground1,
    borderRightWidth: tokens.strokeWidthThin,
    borderRightStyle: "solid",
    borderRightColor: tokens.colorNeutralStroke2,
  },
  expanded: {
    width: SIDEBAR_WIDTH_EXPANDED,
  },
  collapsed: {
    width: SIDEBAR_WIDTH_COLLAPSED,
  },
  toggleRow: {
    display: "flex",
    padding: tokens.spacingHorizontalXS,
  },
  nav: {
    display: "flex",
    flexDirection: "column",
    rowGap: tokens.spacingVerticalXS,
    padding: tokens.spacingHorizontalXS,
  },
  navItem: {
    display: "flex",
    alignItems: "center",
    columnGap: tokens.spacingHorizontalM,
    padding: `${tokens.spacingVerticalS} ${tokens.spacingHorizontalM}`,
    borderRadius: tokens.borderRadiusMedium,
    color: tokens.colorNeutralForeground2,
    textDecorationLine: "none",
    whiteSpace: "nowrap",
    overflow: "hidden",
    fontSize: tokens.fontSizeBase300,
    ":hover": {
      backgroundColor: tokens.colorNeutralBackground1Hover,
    },
  },
  navItemActive: {
    color: tokens.colorPaletteDarkRedForeground2,
    backgroundColor: tokens.colorPaletteDarkRedBackground2,
    ":hover": {
      backgroundColor: tokens.colorPaletteDarkRedBackground2,
    },
  },
  navItemIcon: {
    flexShrink: 0,
    fontSize: "20px",
  },
});

export function Sidebar() {
  const styles = useStyles();
  const [collapsed, setCollapsed] = useState(false);

  return (
    <nav
      className={mergeClasses(
        styles.root,
        collapsed ? styles.collapsed : styles.expanded,
      )}
    >
      <div className={styles.toggleRow}>
        <Button
          appearance="transparent"
          icon={<NavigationRegular />}
          onClick={() => setCollapsed((prev) => !prev)}
          aria-label={collapsed ? "Espandi menu" : "Comprimi menu"}
        />
      </div>

      <div className={styles.nav}>
        {APP_SECTION_LIST.map(({ label, path, icon: Icon }) => (
          <Link
            key={path}
            to={path}
            className={styles.navItem}
            activeProps={{ className: styles.navItemActive }}
          >
            <Icon className={styles.navItemIcon} />
            {!collapsed && label}
          </Link>
        ))}
      </div>
    </nav>
  );
}
