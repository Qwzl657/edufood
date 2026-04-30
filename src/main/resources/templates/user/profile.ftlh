<#import "../layout/base.ftlh" as layout>
<@layout.base cartCount=cartCount username=username!"">

    <h2 class="mb-4">
        <i class="bi bi-person-circle"></i> Мои заказы
    </h2>

    <#if orders?has_content>
        <#list orders as order>
            <div class="card mb-3 shadow-sm">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <span class="fw-bold">Заказ #${order.id}</span>
                    <span class="text-muted small">${order.createdAt}</span>
                </div>
                <div class="card-body p-0">
                    <ul class="list-group list-group-flush">
                        <#list order.items as item>
                            <li class="list-group-item d-flex justify-content-between">
                    <span>
                        ${item.dish.name}
                        <span class="text-muted">× ${item.quantity}</span>
                    </span>
                                <span class="fw-bold">
                        ${item.price * item.quantity} ₽
                    </span>
                            </li>
                        </#list>
                    </ul>
                </div>
                <div class="card-footer text-end fw-bold text-success">
                    Итого: ${order.totalPrice} ₽
                </div>
            </div>
        </#list>
    <#else>
        <div class="alert alert-info">
            У вас пока нет заказов. <a href="/cafes">Сделать первый заказ</a>
        </div>
    </#if>

</@layout.base>