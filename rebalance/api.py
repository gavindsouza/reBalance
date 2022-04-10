import frappe


@frappe.whitelist(allow_guest=True, methods=["POST"])
def add(*args, **kwargs):
    # TODO: Check user perms & auth
    return frappe.get_doc(
        {
            "doctype": "Phone Notification",
            "title": kwargs.get("title"),
            "text": kwargs.get("text"),
            "post_time": int(kwargs.get("post_time", 0)),
            "tag": kwargs.get("tag"),
            "package": kwargs.get("package"),
            "user": kwargs.get("user"),
            "id": int(kwargs.get("id", 0)),
        }
    ).insert(ignore_permissions=True)


@frappe.whitelist(allow_guest=True, methods=["POST", "DELETE"])
def remove(*args, **kwargs):
    # TODO: Check perms & auth
    doc = frappe.get_doc(
        "Phone Notification",
        {
            "title": kwargs.get("title"),
            "text": kwargs.get("text"),
            "post_time": kwargs.get("post_time"),
            "tag": kwargs.get("tag"),
            "package": kwargs.get("package"),
            "user": kwargs.get("user"),
            "id": kwargs.get("id"),
        },
    )
    return doc.db_set("is_removed", True)


@frappe.whitelist(allow_guest=True)
def get(category):
    return frappe.get_all(
        "Phone Notification",
        filters={"is_removed": False, "category": category},
        fields="*",
        limit=20,
        order_by="post_time desc",
    )
