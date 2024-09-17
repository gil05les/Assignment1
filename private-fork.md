# Creating a Private Fork of the Assignment Repository

To create a private fork of the assignment repository and set up your own repository, follow these steps:

## 1. Fork the Assignment Repository and Set Up a Private Repository

Your first step is to fork the Git repository and create a private repository under your GitHub account.

### Steps:

1. **Create a Bare Clone of the Original Repository (Temporary Step)**  

   Run the following command to create a bare clone of the original repository. This clone will be removed after setting up the private repository. Replace `AssignmentX` with the actual name of the repository (e.g., `Assignment1`).
   ```bash
   git clone --bare git@github.com:HSG-DS-HS24/AssignmentX.git
   ```

2. **Create a New Private Repository on GitHub**  

   Go to GitHub and create a new private repository named `AssignmentX`.

3. **Mirror-Push the Bare Clone to Your New Private Repository**  

   Replace `<your_username>` with your GitHub username in the command below to mirror-push the repository.
   ```bash
   cd AssignmentX.git
   git push --mirror git@github.com:<your_username>/AssignmentX.git
   ```

4. **Remove the Temporary Bare Clone Repository**  

   After pushing the bare clone to your private repository, remove the temporary local clone.
   ```bash
   cd ..
   rm -rf AssignmentX.git
   ```

5. **Clone Your New Private Repository**  

   Now, clone your private repository to your local machine using the following command:
   ```bash
   git clone git@github.com:<your_username>/AssignmentX.git
   ```

## 2. Add the Original Repository as a Remote (Optional)

To keep your fork up-to-date with the original repository, you can add it as a remote with fetch-only access (no pushes allowed).

### Steps:

1. **Add the Upstream Remote**  

   Add the original repository as an upstream remote and disable pushing to it:
   ```bash
   git remote add upstream git@github.com:HSG-DS-HS24/AssignmentX.git
   git remote set-url --push upstream DISABLE
   ```

2. **Verify Remotes**  

   Check that your remotes are configured correctly with the following command:
   ```bash
   git remote -v
   ```
   The output should look like this:
   ```
   origin   git@github.com:<your_username>/AssignmentX.git (fetch)
   origin   git@github.com:<your_username>/AssignmentX.git (push)
   upstream git@github.com:HSG-DS-HS23/AssignmentX.git (fetch)
   upstream DISABLE (push)
   ```

3. **Fetch and Rebase from Upstream**  

   If you want to pull changes from the original repository in the future, run the following commands:
   ```bash
   git fetch upstream
   git rebase upstream/master
   ```

By following these steps, you’ll have a private fork of the assignment repository that you can manage independently while keeping it updated with the original source when necessary.

## Acknowledgments

These instructions are based on a [guide written by 0xjac](https://gist.github.com/0xjac/85097472043b697ab57ba1b1c7530274).
