document.getElementById("btn_cat").addEventListener("click", async () => {
    const categoria = {
        nome: document.getElementById("cat_nome").value,
        desc: document.getElementById("cat_desc").value
    };

    try {
        const resp = await fetch("http://localhost:8080/categorias/salvar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(categoria)
        });

        if (resp.ok) {
            alert("Categoria criada com sucesso!");
        } else {
            alert("Erro ao criar categoria.");
        }
    } catch (err) {
        console.error("Erro:", err);
    }
});

document.getElementById("btn_task").addEventListener("click", async () => {
    const tarefa = {
        titulo: document.getElementById("task_titulo").value,
        desc: document.getElementById("task_desc").value,
        prioridade: document.getElementById("prioridade").value,
        dt_ini: document.getElementById("dt_ini").value,
        dt_fim: document.getElementById("dt_fim").value,
        categoriaId: parseInt(document.getElementById("task_categoria").value)
    };

    try {
        const resp = await fetch("http://localhost:8080/tarefas/salvar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(tarefa)
        });

        if (resp.ok) {
            alert("Tarefa criada com sucesso!");
        } else {
            alert("Erro ao criar tarefa.");
        }
    } catch (err) {
        console.error("Erro:", err);
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const categoriaSelect = document.getElementById("task_categoria");

    fetch("http://localhost:8080/categorias/listar")
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro ao carregar categorias");
            }
            return response.json();
        })
        .then(categorias => {
            categoriaSelect.innerHTML = ""; // limpa opções
            categorias.forEach(cat => {
                const option = document.createElement("option");
                option.value = cat.id; // valor = ID da categoria
                option.textContent = cat.nome; // mostra nome no combo
                categoriaSelect.appendChild(option);
            });
        })
        .catch(err => {
            console.error(err);
            categoriaSelect.innerHTML = "<option value=''>Erro ao carregar</option>";
        });
});
