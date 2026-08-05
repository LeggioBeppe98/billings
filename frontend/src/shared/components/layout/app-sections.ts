import {
  DataBarVerticalRegular,
  PaymentRegular,
  PersonRegular,
  TagRegular,
  type FluentIcon,
} from "@fluentui/react-icons";

export const AppSection = {
  PaymentMethods: "metodi-di-pagamento",
  Tariffs: "tariffe",
  Clients: "clienti",
  Reports: "report",
} as const;

export type AppSection = (typeof AppSection)[keyof typeof AppSection];

type AppSectionConfig = {
  label: string;
  path: `/${AppSection}`;
  icon: FluentIcon;
};

export const APP_SECTIONS: Record<AppSection, AppSectionConfig> = {
  [AppSection.PaymentMethods]: {
    label: "Metodi di pagamento",
    path: `/${AppSection.PaymentMethods}`,
    icon: PaymentRegular,
  },
  [AppSection.Tariffs]: {
    label: "Tariffe",
    path: `/${AppSection.Tariffs}`,
    icon: TagRegular,
  },
  [AppSection.Clients]: {
    label: "Clienti",
    path: `/${AppSection.Clients}`,
    icon: PersonRegular,
  },
  [AppSection.Reports]: {
    label: "Report",
    path: `/${AppSection.Reports}`,
    icon: DataBarVerticalRegular,
  },
};

export const APP_SECTION_LIST = Object.values(AppSection).map(
  (section) => APP_SECTIONS[section],
);
