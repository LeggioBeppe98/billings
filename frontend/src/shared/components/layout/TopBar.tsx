// src/shared/components/layout/TopBar.tsx
import {
  Avatar,
  Button,
  Dialog,
  DialogActions,
  DialogBody,
  DialogContent,
  DialogSurface,
  DialogTitle,
  DialogTrigger,
  Menu,
  MenuItem,
  MenuList,
  MenuPopover,
  MenuTrigger,
  Text,
  makeStyles,
  tokens,
} from "@fluentui/react-components";
import { ChevronDownRegular } from "@fluentui/react-icons";
import { useNavigate } from "@tanstack/react-router";
import { useState } from "react";
import logo from "@/assets/logo.svg";
import { tokenStorage } from "@/shared/lib/token-storage";
import { decodeJwtPayload } from "@/shared/lib/jwt";

const useStyles = makeStyles({
  root: {
    display: "flex",
    alignItems: "center",
    justifyContent: "space-between",
    height: "56px",
    flexShrink: 0, // non deve mai restringersi, anche se il contenuto sotto cresce
    paddingLeft: tokens.spacingHorizontalM,
    paddingRight: tokens.spacingHorizontalM,
    backgroundColor: tokens.colorNeutralBackground1,
    borderBottomWidth: tokens.strokeWidthThin,
    borderBottomStyle: "solid",
    borderBottomColor: tokens.colorNeutralStroke2,
  },
  logo: {
    height: "32px",
  },
  user: {
    display: "flex",
    alignItems: "center",
    columnGap: tokens.spacingHorizontalS,
    cursor: "pointer",
    borderRadius: tokens.borderRadiusMedium,
    padding: tokens.spacingHorizontalXS,
    ":hover": {
      backgroundColor: tokens.colorNeutralBackground1Hover,
    },
  },
});

// L'access token contiene solo l'email (claim "sub"): non esiste ancora un
// endpoint "utente corrente" da cui leggere nome e cognome.
function useCurrentUserEmail(): string | null {
  const token = tokenStorage.getAccessToken();
  if (!token) return null;
  return decodeJwtPayload<{ sub: string }>(token)?.sub ?? null;
}

export function TopBar() {
  const styles = useStyles();
  const navigate = useNavigate();
  const email = useCurrentUserEmail();
  const [changePasswordOpen, setChangePasswordOpen] = useState(false);

  const handleLogout = () => {
    tokenStorage.clear();
    navigate({ to: "/" });
  };

  return (
    <div className={styles.root}>
      <img src={logo} alt="Logo palestra" className={styles.logo} />

      <Menu>
        <MenuTrigger disableButtonEnhancement>
          <div className={styles.user} tabIndex={0} role="button">
            <Avatar name={email ?? undefined} color="dark-red" />
            <Text weight="semibold">{email}</Text>
            <ChevronDownRegular />
          </div>
        </MenuTrigger>
        <MenuPopover>
          <MenuList>
            <MenuItem onClick={() => setChangePasswordOpen(true)}>
              Cambio Password
            </MenuItem>
            <MenuItem onClick={handleLogout}>Logout</MenuItem>
          </MenuList>
        </MenuPopover>
      </Menu>

      <Dialog
        open={changePasswordOpen}
        onOpenChange={(_, data) => setChangePasswordOpen(data.open)}
      >
        <DialogSurface>
          <DialogBody>
            <DialogTitle>Cambio Password</DialogTitle>
            <DialogContent>
              Funzionalità non ancora disponibile.
            </DialogContent>
            <DialogActions>
              <DialogTrigger disableButtonEnhancement>
                <Button appearance="primary">Chiudi</Button>
              </DialogTrigger>
            </DialogActions>
          </DialogBody>
        </DialogSurface>
      </Dialog>
    </div>
  );
}
