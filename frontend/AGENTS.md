# Regole di progetto — Gestionale Palestra

Queste regole sono vincolanti per qualsiasi modifica al codice in questo repository. In caso di conflitto tra queste regole e una richiesta generica, seguire queste regole e segnalare il conflitto.

---

## 1. Dominio applicativo

Applicazione client-server per la gestione contabile di una palestra: registrazione dei pagamenti mensili dei clienti e tracciamento dello stato (non pagato / pagato / fatturato).

### 1.1 Entità

- **Utente**: operatore/admin che usa l'applicazione (non il cliente della palestra).
- **Cliente**: iscritto alla palestra.
- **Tariffa**: listino quote (nome, importo, flag `attiva`).
- **MetodoPagamento**: es. Contanti, Carta, Bonifico.
- **Pagamento**: registrazione mensile. Contiene FK dirette a `cliente`, `tariffa`, `metodo_pagamento`, `utente` — **non esistono tabelle ponte/M:N** per queste relazioni, sono tutte 1-a-N.

### 1.2 Regole di business non negoziabili

- Un pagamento appartiene a **un solo** cliente, **una sola** tariffa, **un solo** metodo di pagamento. Non introdurre split o relazioni many-to-many su queste associazioni senza conferma esplicita.
- Un cliente ha **al massimo un pagamento per mese** (vincolo `UNIQUE(cliente_id, mese)`). Non introdurre pagamenti parziali/multipli per lo stesso mese.
- Lo **stato del cliente è sempre derivato**, mai salvato come colonna:
  - nessun pagamento per il mese → `non_pagato`
  - pagamento presente, `fatturato = false` → `pagato`
  - pagamento presente, `fatturato = true` → `fatturato`
- `Pagamento.importo` è **storicizzato al momento del pagamento**, non derivato dal prezzo corrente di `Tariffa`. Non sostituire con una join sul prezzo attuale della tariffa.
- `Tariffa` e `MetodoPagamento` usano **soft-delete** (flag `attiva`/`attivo`). Non fare `DELETE` fisico su righe referenziate da pagamenti storici.

### 1.3 Schema di riferimento (Postgres)

```sql
CREATE TABLE pagamento (
  id SERIAL PRIMARY KEY,
  cliente_id INT NOT NULL REFERENCES cliente(id),
  tariffa_id INT NOT NULL REFERENCES tariffa(id),
  metodo_pagamento_id INT NOT NULL REFERENCES metodo_pagamento(id),
  utente_id INT NOT NULL REFERENCES utente(id),
  mese DATE NOT NULL,              -- convenzione: sempre giorno 1 del mese
  importo NUMERIC(10,2) NOT NULL,  -- storicizzato, mai derivato da tariffa.importo
  fatturato BOOLEAN NOT NULL DEFAULT false,
  data_pagamento TIMESTAMP NOT NULL DEFAULT now(),
  data_fattura TIMESTAMP,
  UNIQUE (cliente_id, mese)
);
```

---

## 2. Stack tecnico

- **Backend**: Java (progetto esistente, endpoint e gestione utenti già presenti).
- **Frontend**: Vite + React + TypeScript, TanStack Router (file-based), TanStack Query, Zod, Fluent UI (libreria componenti).
- **Database**: PostgreSQL.

---

## 3. Struttura cartelle frontend (obbligatoria)

Organizzazione **per feature**, non per tipo di file. Non creare cartelle globali `components/`, `hooks/`, `types/` con dentro tutto il progetto.

```
src/
├── routes/                    # SOLO file dettati dalla convenzione TanStack Router. Thin: prefetch + mount, niente logica/JSX di pagina.
├── features/<entità>/
│   ├── schemas/                # zod schema — fonte di verità dei tipi
│   ├── api/                    # chiamate HTTP grezze, usano sempre apiClient
│   ├── queries/                # query key factory + queryOptions + mutation hooks
│   ├── components/              # <Entità>Page (smart), <Entità>Table (dumb), <Entità>FormDialog
│   └── index.ts                # barrel: esporta SOLO l'API pubblica della feature (tipicamente <Entità>Page)
├── shared/
│   ├── components/              # componenti davvero generici e riusati da 2+ feature
│   ├── hooks/
│   └── lib/                     # api-client.ts, query-client.ts, env.ts
└── routeTree.gen.ts             # generato dal plugin, NON modificare a mano
```

Regola di promozione: un elemento resta dentro la sua feature finché è usato da una sola feature. Va in `shared/` solo quando serve a 2 o più feature.

`routeTree.gen.ts` non si genera all'installazione dei pacchetti: viene rigenerato al primo `npm run dev`/`build` **dopo** aver configurato `vite.config.ts` **e** aver creato almeno un file di rotta in `src/routes` (es. `__root.tsx`).

---

## 4. Regole obbligatorie — TanStack Query

- **Mai chiamare `useQuery`/`useMutation` direttamente in un componente.** Sempre tramite hook/queryOptions esportati da `features/<entità>/queries/`.
- Ogni feature definisce una **query key factory** (`<entità>Keys.all`, `.list()`, `.detail(id)`); non usare stringhe hardcoded come query key.
- Dopo ogni mutation di successo, `invalidateQueries` sulla key `.all` della feature, salvo necessità reale di optimistic update.
- I loader delle rotte TanStack Router usano `queryClient.ensureQueryData(...)` per prefetchare — non fare fetch nel `useEffect` del componente.

## 5. Regole obbligatorie — Zod

- Ogni entità ha uno schema zod come fonte di verità; il tipo TypeScript è sempre `z.infer<typeof schema>`, mai un'interfaccia scritta a mano in parallelo.
- Validare con zod: risposte API non fidate e input dei form. Non validare dati già tipizzati e puramente interni.
- Form con `react-hook-form` + `@hookform/resolvers/zod`, non validazione manuale sparsa nei componenti.
- Variabili d'ambiente validate a startup in `shared/lib/env.ts`.

## 6. Regole obbligatorie — componenti e codice

- UI basata su **Fluent UI**: usare i componenti della libreria (`Button`, `Dialog`, `DataGrid`/`Table`, `Input`, ecc.) invece di scriverne di custom da zero. Componenti custom in `shared/components/` solo per ciò che Fluent UI non copre.
- Un componente = una responsabilità. Separare sempre "smart" (`*Page.tsx`: fetch + stato + handler) da "dumb" (`*Table.tsx`, `*Form*.tsx`: solo props, nessun accesso a query/mutation).
- Import assoluti con alias `@/`, mai `../../../`.
- Niente `fetch()` diretto nei componenti o nei file `api/`: sempre tramite `apiClient` centralizzato (`shared/lib/api-client.ts`).
- Niente `any` o `as` non giustificati: se serve un cast, verificare prima se manca un parse zod.
- Naming: `PascalCase.tsx` per componenti, `useCamelCase.ts` per hook, `camelCase.ts` per util/api.

## 7. Cosa NON fare

- Non introdurre tabelle ponte/M:N per Cliente-Pagamento, Pagamento-Tariffa, Pagamento-MetodoPagamento.
- Non salvare lo stato del cliente come colonna: resta sempre derivato a runtime.
- Non modificare manualmente `routeTree.gen.ts`.
- Non usare Redux/Zustand per stato server: quello è compito di TanStack Query. Stato UI locale con `useState`/context solo se davvero condiviso tra componenti distanti.
