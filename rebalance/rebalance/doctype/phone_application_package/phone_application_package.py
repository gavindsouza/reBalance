# Copyright (c) 2022, Gavin D'souza and contributors
# For license information, please see license.txt

from frappe.model.document import Document

DEFAULT_CATEGORIES = {
    "Important": ["Phone", "Messaging", "Messages", "Calendar", "Play Games"],
    "Updates": ["Telegram", "Email", "Gmail", "Kite", "Smallcase", "Coin", "Chrome"],
    "Forums": ["Stackoverflow", "LinkedIn", "Instagram", "Twitter", "Reddit"],
    "Promotions": ["Amazon", "Flipkart", "CRED"],
}


def guess_app_category(app: str) -> str:
    # TODO: Use a system that would be better suited for this (NLP)
    for category, apps in DEFAULT_CATEGORIES.items():
        if app.casefold() in [app.casefold() for app in apps]:
            return category


class PhoneApplicationPackage(Document):
    def validate(self):
        if not self.has_value_changed("app") and not self.app:
            self.app = (self.package or "").rsplit(".", 1)[-1]
        if not self.has_value_changed("category") and not self.category:
            self.category = guess_app_category(self.app)
