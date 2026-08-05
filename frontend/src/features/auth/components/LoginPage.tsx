import {
  Button,
  Card,
  Field,
  Input,
  Text,
  makeStyles,
  tokens,
} from "@fluentui/react-components";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useNavigate } from "@tanstack/react-router";
import { loginSchema, type LoginFormValues } from "../schemas/login.schema";
import { useLoginMutation } from "../queries/auth.queries";
import { ApiError } from "@/shared/lib/api-client";
import logo from "@/assets/logo.svg";

const useStyles = makeStyles({
  container: {
    display: "flex",
    minHeight: "100vh",
  },
  left: {
    flex: 1,
    backgroundColor: tokens.colorNeutralBackground3,
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  },
  logo: {
    width: "60%",
    maxWidth: "300px",
    "@media (max-height: 700px)": {
      maxWidth: "180px",
    },
  },
  right: {
    flex: 1,
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    padding: "2rem",
    "@media (max-height: 700px)": {
      padding: "1rem",
    },
  },
  card: {
    width: "100%",
    maxWidth: "380px",
    padding: "2rem",
    display: "flex",
    flexDirection: "column",
    rowGap: "1rem",
    "@media (max-height: 700px)": {
      padding: "1rem",
      rowGap: "0.5rem",
    },
  },
});

export function LoginPage() {
  const styles = useStyles();
  const navigate = useNavigate();
  const loginMutation = useLoginMutation();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
  });

  const onSubmit = (data: LoginFormValues) => {
    loginMutation.mutate(data, {
      onSuccess: () => navigate({ to: "/home" }),
    });
  };

  const errorMessage =
    loginMutation.error instanceof ApiError
      ? loginMutation.error.message
      : loginMutation.isError
        ? "Errore di comunicazione con il server"
        : undefined;

  return (
    <div className={styles.container}>
      <div className={styles.left}>
        <img src={logo} alt="Logo palestra" className={styles.logo} />
      </div>

      <div className={styles.right}>
        <Card className={styles.card}>
          <Text size={600} weight="semibold" align="center">
            Accedi
          </Text>

          <form onSubmit={handleSubmit(onSubmit)}>
            <div
              style={{
                display: "flex",
                flexDirection: "column",
                rowGap: "1rem",
              }}
            >
              <Field
                label="Email"
                validationState={errors.email ? "error" : undefined}
                validationMessage={errors.email?.message}
              >
                <Input type="email" {...register("email")} />
              </Field>

              <Field
                label="Password"
                validationState={errors.password ? "error" : undefined}
                validationMessage={errors.password?.message}
              >
                <Input type="password" {...register("password")} />
              </Field>

              {errorMessage && (
                <Text style={{ color: tokens.colorPaletteRedForeground1 }}>
                  {errorMessage}
                </Text>
              )}

              <Button
                type="submit"
                appearance="primary"
                disabled={loginMutation.isPending}
              >
                Login
              </Button>
            </div>
          </form>
        </Card>
      </div>
    </div>
  );
}
