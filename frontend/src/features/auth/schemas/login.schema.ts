import { z } from "zod";

export const loginSchema = z.object({
  email: z.string(),
  password: z.string().min(6),
});

export type LoginFormValues = z.infer<typeof loginSchema>;

export const loginResponseSchema = z.object({
  token: z.string(),
  refreshToken: z.string(),
});

export type LoginResponse = z.infer<typeof loginResponseSchema>;
