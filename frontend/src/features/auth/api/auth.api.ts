import { apiClient } from "@/shared/lib/api-client";
import {
  loginResponseSchema,
  type LoginFormValues,
  type LoginResponse,
} from "../schemas/login.schema";

export function loginRequest(
  credentials: LoginFormValues,
): Promise<LoginResponse> {
  return apiClient
    .post<unknown>("/auth/login", credentials)
    .then((data) => loginResponseSchema.parse(data));
}
