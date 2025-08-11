document.addEventListener("DOMContentLoaded", () => {
    const baseUrl = "http://localhost:8080"; // ajuste se preciso
    const taskList = document.getElementById("task-list");

    // helpers
    const first = (...vals) => {
        for (const v of vals) if (v !== undefined && v !== null) return v;
        return null;
    };

    const parseToDate = (s) => {
        if (!s) return null;
        if (s instanceof Date) return s;
        // timestamp number
        if (!isNaN(s) && (typeof s === "number" || /^\d+$/.test(String(s)))) return new Date(Number(s));
        // ISO with time or Z -> safe to use Date
        if (/[TtZ]/.test(s)) return new Date(s);
        // yyyy-mm-dd (treat as local date to avoid timezone shift)
        const m = s.match(/^(\d{4})-(\d{2})-(\d{2})$/);
        if (m) return new Date(Number(m[1]), Number(m[2]) - 1, Number(m[3]));
        // fallback
        return new Date(s);
    };

    const toDateOnly = (d) => new Date(d.getFullYear(), d.getMonth(), d.getDate());
    const formatDateBr = (d) => d.toLocaleDateString("pt-BR", { weekday: "short", day: "numeric", month: "short" });

    // tenta atualizar status com várias opções (fallbacks)
    async function updateStatusOnServer(id, novoStatus) {
        const tries = [
            { url: `${baseUrl}/tarefas/concluir/${id}`, method: "PATCH", body: null }, // endpoint sem body
            { url: `${baseUrl}/tarefas/concluir/${id}`, method: "PATCH", body: { status: novoStatus } }, // endpoint com body
            { url: `${baseUrl}/tarefas/${id}/status`, method: "PUT", body: { status: novoStatus } }, // PUT /{id}/status
            { url: `${baseUrl}/tarefas/${id}`, method: "PATCH", body: { status: novoStatus } } // PATCH no recurso
        ];

        let lastErr = null;
        for (const attempt of tries) {
            try {
                const options = {
                    method: attempt.method,
                    headers: attempt.body ? { "Content-Type": "application/json" } : undefined,
                    body: attempt.body ? JSON.stringify(attempt.body) : undefined
                };
                console.log("Tentando atualizar status ->", attempt.url, options);
                const resp = await fetch(attempt.url, options);
                if (resp.ok) {
                    return { ok: true, url: attempt.url, status: resp.status, text: await resp.text() };
                } else {
                    lastErr = { ok: false, url: attempt.url, status: resp.status, text: await resp.text() };
                    // se 4xx/5xx, tenta próximo fallback
                }
            } catch (err) {
                lastErr = { error: err, url: attempt.url };
            }
        }
        throw lastErr;
    }

    // renderiza tarefas
    function renderTasks(tarefas) {
        taskList.innerHTML = "";
        const hoje = toDateOnly(new Date());

        tarefas.forEach(t => {
            // mapping defensivo de campos (tente essas chaves comuns)
            const id = first(t.id, t.taskId, t.tarefaId);
            const titulo = first(t.titulo, t.nome, t.title) || "Sem título";
            const descricao = first(t.descricao, t.description, t.desc) || "";
            // categoria pode ser objeto ou string
            const categoriaRaw = first(t.categoria, t.categoria_nome, t.categoriaNome, t.category);
            let categoriaNome = "";
            if (categoriaRaw) {
                if (typeof categoriaRaw === "string") categoriaNome = categoriaRaw;
                else categoriaNome = first(categoriaRaw.nome, categoriaRaw.titulo, categoriaRaw.name, "");
            }
            // data fim: várias possiblidades de nome
            const dtFimStr = first(t.dtFim, t.dt_fim, t.dataFim, t.data_fim, t.fim, t.deadline);
            const dtFim = parseToDate(dtFimStr);

            const prioridadeRaw = (first(t.prioridade, t.priority) || "").toString().toUpperCase();
            const statusRaw = (first(t.status, t.situacao, t.estado) || "").toString().toUpperCase();

            // construção do card
            const card = document.createElement("div");
            card.className = "task-card";

            // classes por estado
            if (statusRaw.includes("CONCL")) { // CONCLUIDO
                card.classList.add("concluida");
            } else if (dtFim && toDateOnly(dtFim) < hoje) {
                card.classList.add("atrasada");
            } else {
                card.classList.add("padrao");
            }

            // header: titulo + checkbox no canto
            const header = document.createElement("div");
            header.className = "task-header";
            const h3 = document.createElement("h3");
            h3.textContent = titulo;
            const sub = document.createElement("div"); // subtitulo categoria
            sub.style.fontSize = "0.85rem";
            sub.style.color = "#777";
            sub.textContent = categoriaNome;

            const left = document.createElement("div");
            left.appendChild(h3);
            left.appendChild(sub);

            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.checked = statusRaw.includes("CONCL"); // se tiver "CONCLUIDO" ou "CONCLUID"
            checkbox.title = "Marcar como concluída";

            // evento de marcar
            checkbox.addEventListener("change", async () => {
                const novoStatus = checkbox.checked ? "CONCLUIDO" : "EM_ANDAMENTO";
                checkbox.disabled = true;
                try {
                    const result = await updateStatusOnServer(id, novoStatus);
                    console.log("Atualizado:", result);
                    // atualiza a UI sem reload
                    card.classList.remove("atrasada", "concluida", "padrao");
                    if (novoStatus === "CONCLUIDO") {
                        card.classList.add("concluida");
                    } else if (dtFim && toDateOnly(dtFim) < hoje) {
                        card.classList.add("atrasada");
                    } else {
                        card.classList.add("padrao");
                    }
                    // atualiza o texto do status (procura elemento)
                    const statusEl = card.querySelector(".task-status");
                    if (statusEl) {
                        statusEl.textContent = novoStatus === "CONCLUIDO" ? "Concluído" : (dtFim && toDateOnly(dtFim) < hoje ? "Atrasado" : "Em andamento");
                        statusEl.className = "task-status " + (novoStatus === "CONCLUIDO" ? "status-concluido" : (dtFim && toDateOnly(dtFim) < hoje ? "status-atrasado" : "status-andamento"));
                    }
                } catch (err) {
                    console.error("Erro ao atualizar status:", err);
                    alert("Erro ao atualizar status. Veja console para detalhes.");
                    // reverte checkbox
                    checkbox.checked = !checkbox.checked;
                } finally {
                    checkbox.disabled = false;
                }
            });

            const right = document.createElement("div");
            right.appendChild(checkbox);

            header.appendChild(left);
            header.appendChild(right);

            // prioridade
            const pri = document.createElement("div");
            pri.className = "task-prioridade";
            if (prioridadeRaw === "BAIXA") {
                pri.classList.add("prioridade-baixa");
                pri.textContent = "Prioridade Baixa";
            } else if (prioridadeRaw === "MEDIA" || prioridadeRaw === "MÉDIA") {
                pri.classList.add("prioridade-media");
                pri.textContent = "Prioridade Média";
            } else {
                pri.classList.add("prioridade-alta");
                pri.textContent = "Prioridade Alta";
            }

            // data de fim
            const dataFimDiv = document.createElement("div");
            if (dtFim) dataFimDiv.innerHTML = `📅 ${formatDateBr(dtFim)}`;

            // status
            const statusDiv = document.createElement("div");
            statusDiv.className = "task-status";
            if (statusRaw.includes("CONCL")) {
                statusDiv.classList.add("status-concluido");
                statusDiv.textContent = "Concluído";
            } else if (dtFim && toDateOnly(dtFim) < hoje) {
                statusDiv.classList.add("status-atrasado");
                statusDiv.textContent = "Atrasado";
            } else {
                statusDiv.classList.add("status-andamento");
                statusDiv.textContent = "Em andamento";
            }

            // descricao
            const descDiv = document.createElement("div");
            descDiv.className = "task-desc";
            descDiv.textContent = descricao;

            // monta card
            card.appendChild(header);
            card.appendChild(pri);
            card.appendChild(dataFimDiv);
            card.appendChild(statusDiv);
            card.appendChild(descDiv);

            taskList.appendChild(card);
        });
    }

    // fetch das tarefas
    fetch(`${baseUrl}/tarefas/listar`)
        .then(res => {
            if (!res.ok) throw new Error("Erro ao buscar tarefas: " + res.status);
            return res.json();
        })
        .then(data => {
            console.log("tarefas recebidas:", data);
            renderTasks(Array.isArray(data) ? data : (data.content || []));
        })
        .catch(err => {
            console.error("Erro ao carregar tarefas", err);
            taskList.innerHTML = "<p>Erro ao carregar tarefas. Veja console.</p>";
        });
});
